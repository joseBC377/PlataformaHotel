package com.example.hotel.ai.service;

import com.example.hotel.ai.tools.HabitacionTool;
import com.example.hotel.ai.tools.ReservaTool;
import com.example.hotel.ai.tools.ServicioTool;

import com.example.hotel.entities.Usuario;
import com.example.hotel.services.UsuarioService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatAIService {

    // ==========================================================
    // CONFIGURACIÓN
    // ==========================================================

    private static final String MODEL =
            "openai/gpt-oss-120b";

    private static final int MAX_HISTORIAL = 12;

    // ==========================================================
    // DEPENDENCIAS
    // ==========================================================

    private final RestClient groqRestClient;

    private final HabitacionTool habitacionTool;

    private final ServicioTool servicioTool;

    private final ReservaTool reservaTool;

    private final UsuarioService usuarioService;

    private final ObjectMapper objectMapper;

    // ==========================================================
    // MEMORIA TEMPORAL POR USUARIO
    // ==========================================================

    private final Map<String, List<Map<String, Object>>> conversaciones =
            new ConcurrentHashMap<>();

    // ==========================================================
    // CONSTRUCTOR
    // ==========================================================

    public ChatAIService(
            @Qualifier("groqRestClient")
            RestClient groqRestClient,
            HabitacionTool habitacionTool,
            ServicioTool servicioTool,
            ReservaTool reservaTool,
            UsuarioService usuarioService,
            ObjectMapper objectMapper) {

        this.groqRestClient =
                groqRestClient;

        this.habitacionTool =
                habitacionTool;

        this.servicioTool =
                servicioTool;

        this.reservaTool =
                reservaTool;

        this.usuarioService =
                usuarioService;

        this.objectMapper =
                objectMapper;
    }

    // ==========================================================
    // RESPONDER
    // ==========================================================

    public String responder(
            String mensaje) {

        if (mensaje == null || mensaje.isBlank()) {
            throw new RuntimeException("El mensaje no puede estar vacío.");
        }

        String identificadorUsuario = obtenerIdentificadorUsuario();
        List<Map<String, Object>> messages = new ArrayList<>();
        LocalDate fechaActual = LocalDate.now();

        String systemPrompt = """
                Eres Royal Concierge, el asistente virtual del Hotel Royal Suites.
                Ayudas con habitaciones, disponibilidad, servicios y reservas.

                FECHA ACTUAL DEL SISTEMA: %s

                REGLAS:
                1. Responde en español, breve, natural y profesional.
                2. Nunca inventes habitaciones, servicios, precios, disponibilidad, fechas ni reservas.
                3. Usa solo datos reales devueltos por las funciones.
                4. Para listar el catálogo usa consultar_habitaciones sin fechas.
                5. Una habitación del catálogo NO implica disponibilidad para una estadía.
                6. Para disponibilidad necesitas fecha de ingreso y salida. Si falta una, pregunta solo por ella.
                7. Con ambas fechas usa consultar_habitaciones con fechaInicio y fechaFin.
                8. Solo las habitaciones devueltas por esa consulta pueden llamarse disponibles.
                9. Para servicios usa consultar_servicios_hotel.
                10. Para reservar necesitas idHabitacion, fechaInicio y fechaFin.
                11. Si falta un dato para reservar, pregunta solo por ese dato.
                12. Con los tres datos usa solicitar_reserva_habitacion.
                13. Nunca confirmes una reserva antes del resultado exitoso de la función.
                14. Las reservas nuevas se registran como PENDIENTE.
                15. No pidas ID de usuario, correo ni contraseña.

                FECHAS Y CONTEXTO:
                16. Envía fechas a las funciones en formato YYYY-MM-DD.
                17. Usa la FECHA ACTUAL DEL SISTEMA para interpretar "hoy", "mañana",
                    "este viernes", "20 de diciembre" y expresiones similares.
                18. Si el usuario da día y mes sin año, usa la próxima ocurrencia válida
                    que no sea anterior a la fecha actual.
                19. No conviertas una fecha futura en una fecha pasada.
                20. Conserva fecha de ingreso, fecha de salida y habitación mientras
                    el usuario siga hablando de la misma reserva.
                21. Si el usuario cambia un dato explícitamente, usa el dato nuevo.
                22. Si responde "la 5" o "habitación 5" después de mostrar habitaciones,
                    interpreta idHabitacion = 5.
                23. Si el usuario dice "disponibles hoy", usa hoy como ingreso solo si
                    esa intención es clara, pero todavía necesitas una fecha de salida.

                RESERVAS, PRECIOS Y TOTAL:
                24. La función de reserva vuelve a comprobar disponibilidad.
                25. Si devuelve RESERVA_NO_CREADA, informa que no se creó la reserva.
                26. Si devuelve RESERVA_CREADA, presenta exactamente los datos devueltos.
                27. El precio recibido es precio por noche y debe mostrarse con S/.
                28. El total NO lo calcules tú. Usa exclusivamente el total devuelto
                    por solicitar_reserva_habitacion.
                29. Cuando exista una reserva creada, muestra número de reserva,
                    habitación, ingreso, salida, noches, precio por noche, total y estado.
                30. No uses el símbolo $ para precios de habitaciones.

                FORMATO:
                31. No uses tablas Markdown, columnas con | ni separadores ---.
                32. No escribas "Continue".
                33. No repitas listas dentro de una misma respuesta.
                34. No muestres errores técnicos internos.
                35. Usa respuestas fáciles de leer y emojis discretos.
                36. No menciones Groq, Whisper, API, Function Calling, Tool, JSON ni base de datos.
                37. Si algo es ambiguo, pide una aclaración breve.
                38. No digas "disponible ahora" sin haber consultado ingreso y salida.

                Formato sugerido de habitación:
                🏨 H-301 — Familiar
                👥 Capacidad: 6 personas
                💰 S/ 250.00 por noche
                🔖 Habitación #3

                Formato sugerido de reserva creada:
                ✨ Solicitud de reserva registrada
                🏨 Habitación: H-501 — Doble
                📅 Ingreso: 20/12/2026
                📅 Salida: 24/12/2026
                🌙 Estadía: 4 noches
                💰 Precio por noche: S/ 120.00
                💳 Total: S/ 480.00
                🔖 Reserva #20
                🟡 Estado: PENDIENTE
                """.formatted(fechaActual);

        messages.add(Map.of(
                "role", "system",
                "content", systemPrompt
        ));

        messages.addAll(obtenerHistorial(identificadorUsuario));

        Map<String, Object> userMessage = new LinkedHashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", mensaje);
        messages.add(userMessage);

        List<Map<String, Object>> tools = crearTools();

        // Permite varias rondas de Function Calling en el mismo mensaje.
        final int MAX_ITERACIONES_TOOLS = 6;

        // Evita crear dos veces la misma reserva si el modelo repite la llamada.
        Map<String, String> resultadosToolsEjecutados = new HashMap<>();

        for (int iteracion = 0;
             iteracion < MAX_ITERACIONES_TOOLS;
             iteracion++) {

            JsonNode response = llamarGroq(messages, tools);
            JsonNode assistantNode = obtenerMensaje(response);
            JsonNode toolCalls = assistantNode.get("tool_calls");

            // Si ya no hay tools, esta es la respuesta final.
            if (toolCalls == null
                    || !toolCalls.isArray()
                    || toolCalls.isEmpty()) {

                JsonNode content = assistantNode.get("content");

                if (content == null
                        || content.isNull()
                        || content.asText().isBlank()) {

                    throw new RuntimeException(
                            "El asistente no devolvió una respuesta final válida."
                    );
                }

                String respuestaFinal = content.asText().trim();

                guardarInteraccion(
                        identificadorUsuario,
                        mensaje,
                        respuestaFinal
                );

                return respuestaFinal;
            }

            // Guardar el mensaje assistant que contiene los tool_calls.
            Map<String, Object> assistantMessage = new LinkedHashMap<>();
            assistantMessage.put("role", "assistant");

            if (assistantNode.has("content")
                    && !assistantNode.get("content").isNull()) {
                assistantMessage.put(
                        "content",
                        assistantNode.get("content").asText()
                );
            } else {
                assistantMessage.put("content", null);
            }

            assistantMessage.put(
                    "tool_calls",
                    objectMapper.convertValue(toolCalls, List.class)
            );

            messages.add(assistantMessage);

            // Ejecutar todas las funciones solicitadas en esta ronda.
            for (JsonNode toolCall : toolCalls) {

                String toolCallId = toolCall.get("id").asText();
                JsonNode function = toolCall.get("function");
                String functionName = function.get("name").asText();
                String argumentsJson = function.get("arguments").asText();

                System.out.println(
                        "[AI TOOL] Iteración "
                                + (iteracion + 1)
                                + " - Función: "
                                + functionName
                );

                System.out.println(
                        "[AI TOOL] Argumentos: "
                                + argumentsJson
                );

                String claveOperacion =
                        functionName + "|" + argumentsJson;

                String toolResult;

                if ("solicitar_reserva_habitacion".equals(functionName)
                        && resultadosToolsEjecutados.containsKey(claveOperacion)) {

                    toolResult =
                            resultadosToolsEjecutados.get(claveOperacion);

                    System.out.println(
                            "[AI TOOL] Reserva repetida detectada. "
                                    + "Se reutiliza el resultado anterior."
                    );

                } else {

                    try {
                        toolResult =
                                ejecutarTool(
                                        functionName,
                                        argumentsJson
                                );

                        if ("solicitar_reserva_habitacion".equals(functionName)) {
                            resultadosToolsEjecutados.put(
                                    claveOperacion,
                                    toolResult
                            );
                        }

                        System.out.println(
                                "[AI TOOL] Ejecutada correctamente."
                        );

                    } catch (Exception e) {

                        toolResult =
                                "ERROR_AL_EJECUTAR_FUNCION: "
                                        + e.getMessage();

                        System.out.println(
                                "[AI TOOL] Error: "
                                        + e.getMessage()
                        );
                    }
                }

                Map<String, Object> toolMessage = new LinkedHashMap<>();
                toolMessage.put("role", "tool");
                toolMessage.put("tool_call_id", toolCallId);
                toolMessage.put("name", functionName);
                toolMessage.put("content", toolResult);

                messages.add(toolMessage);
            }
        }

        throw new RuntimeException(
                "El asistente realizó demasiadas operaciones consecutivas. "
                        + "Intenta nuevamente con una solicitud más específica."
        );
    }

    // ==========================================================
    // IDENTIFICAR USUARIO
    // ==========================================================

    private String obtenerIdentificadorUsuario() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(
                authentication.getPrincipal()
        )) {

            return "ANONIMO";
        }

        return authentication.getName();
    }

    // ==========================================================
    // OBTENER HISTORIAL
    // ==========================================================

    private List<Map<String, Object>> obtenerHistorial(
            String usuario) {

        List<Map<String, Object>> historial =
                conversaciones.get(
                        usuario
                );

        if (historial == null) {

            return new ArrayList<>();
        }

        synchronized (historial) {

            return new ArrayList<>(
                    historial
            );
        }
    }

    // ==========================================================
    // GUARDAR INTERACCIÓN
    // ==========================================================

    private void guardarInteraccion(
            String usuario,
            String mensajeUsuario,
            String respuestaAsistente) {

        List<Map<String, Object>> historial =
                conversaciones.computeIfAbsent(
                        usuario,
                        key -> new ArrayList<>()
                );

        synchronized (historial) {

            historial.add(
                    Map.of(
                            "role",
                            "user",

                            "content",
                            mensajeUsuario
                    )
            );

            historial.add(
                    Map.of(
                            "role",
                            "assistant",

                            "content",
                            respuestaAsistente
                    )
            );

            limitarHistorial(
                    historial
            );
        }
    }

    // ==========================================================
    // LIMITAR HISTORIAL
    // ==========================================================

    private void limitarHistorial(
            List<Map<String, Object>> historial) {

        while (historial.size()
                > MAX_HISTORIAL) {

            historial.remove(0);
        }
    }

    // ==========================================================
    // LIMPIAR CONVERSACIÓN
    // ==========================================================

    public void limpiarConversacion() {

        String usuario =
                obtenerIdentificadorUsuario();

        conversaciones.remove(
                usuario
        );

        System.out.println(
                "[AI CONTEXTO] Conversación limpiada para: "
                        + usuario
        );
    }

    // ==========================================================
    // EJECUTAR TOOL
    // ==========================================================

    private String ejecutarTool(
            String functionName,
            String argumentsJson)
            throws Exception {

        JsonNode args =
                objectMapper.readTree(
                        argumentsJson
                );

        return switch (functionName) {

            // ==================================================
            // HABITACIONES
            // ==================================================

            case "consultar_habitaciones" -> {

                boolean tieneFechaInicio =
                        args.hasNonNull(
                                "fechaInicio"
                        );

                boolean tieneFechaFin =
                        args.hasNonNull(
                                "fechaFin"
                        );

                // ==============================================
                // SI ENVÍA SOLO UNA FECHA, NO PERMITIR CONSULTA
                // ==============================================

                if (tieneFechaInicio != tieneFechaFin) {

                    throw new RuntimeException(
                            "Para consultar disponibilidad se necesitan "
                                    + "fecha de ingreso y fecha de salida."
                    );
                }

                // ==============================================
                // CONSULTA REAL DE DISPONIBILIDAD
                // ==============================================

                if (tieneFechaInicio
                        && tieneFechaFin) {

                    LocalDate fechaInicio =
                            LocalDate.parse(
                                    args.get(
                                            "fechaInicio"
                                    ).asText()
                            );

                    LocalDate fechaFin =
                            LocalDate.parse(
                                    args.get(
                                            "fechaFin"
                                    ).asText()
                            );

                    List<Map<String, Object>> habitaciones =
                            habitacionTool
                                    .consultarHabitacionesDisponibles(
                                            fechaInicio,
                                            fechaFin
                                    );

                    yield objectMapper
                            .writeValueAsString(
                                    habitaciones
                            );
                }

                // ==============================================
                // CATÁLOGO GENERAL
                // ==============================================

                List<Map<String, Object>> habitaciones =
                        habitacionTool
                                .consultarHabitaciones();

                yield objectMapper
                        .writeValueAsString(
                                habitaciones
                        );
            }

            // ==================================================
            // SERVICIOS
            // ==================================================

            case "consultar_servicios_hotel" -> {

                List<Map<String, Object>> servicios =
                        servicioTool
                                .consultarServiciosHotel();

                yield objectMapper
                        .writeValueAsString(
                                servicios
                        );
            }

            // ==================================================
            // RESERVA
            // ==================================================

            case "solicitar_reserva_habitacion" -> {

                if (!args.hasNonNull(
                        "idHabitacion"
                )
                        || !args.hasNonNull(
                        "fechaInicio"
                )
                        || !args.hasNonNull(
                        "fechaFin"
                )) {

                    throw new RuntimeException(
                            "Faltan datos necesarios para realizar la reserva."
                    );
                }

                Integer idHabitacion =
                        args.get(
                                "idHabitacion"
                        ).asInt();

                LocalDate fechaInicio =
                        LocalDate.parse(
                                args.get(
                                        "fechaInicio"
                                ).asText()
                        );

                LocalDate fechaFin =
                        LocalDate.parse(
                                args.get(
                                        "fechaFin"
                                ).asText()
                        );

                Usuario usuario =
                        obtenerUsuarioAutenticado();

                yield reservaTool
                        .solicitarReservaHabitacion(
                                idHabitacion,
                                fechaInicio,
                                fechaFin,
                                usuario
                        );
            }

            default ->

                    throw new RuntimeException(
                            "Función desconocida: "
                                    + functionName
                    );
        };
    }

    // ==========================================================
    // USUARIO AUTENTICADO
    // ==========================================================

    private Usuario obtenerUsuarioAutenticado() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(
                authentication.getPrincipal()
        )) {

            throw new RuntimeException(
                    "Debe iniciar sesión para realizar una reserva."
            );
        }

        String correo =
                authentication.getName();

        try {

            return usuarioService
                    .findByCorreo(
                            correo
                    );

        } catch (Exception e) {

            throw new RuntimeException(
                    "No se encontró el usuario autenticado."
            );
        }
    }

    // ==========================================================
    // LLAMAR A GROQ
    // ==========================================================

    private JsonNode llamarGroq(
            List<Map<String, Object>> messages,
            List<Map<String, Object>> tools) {

        Map<String, Object> body =
                new LinkedHashMap<>();

        body.put(
                "model",
                MODEL
        );

        body.put(
                "messages",
                messages
        );

        body.put(
                "tools",
                tools
        );

        body.put(
                "tool_choice",
                "auto"
        );

        JsonNode response =
                groqRestClient
                        .post()
                        .uri(
                                "/chat/completions"
                        )
                        .body(
                                body
                        )
                        .retrieve()
                        .body(
                                JsonNode.class
                        );

        if (response == null) {

            throw new RuntimeException(
                    "Groq no devolvió una respuesta."
            );
        }

        return response;
    }

    // ==========================================================
    // EXTRAER MENSAJE
    // ==========================================================

    private JsonNode obtenerMensaje(
            JsonNode response) {

        if (!response.has(
                "choices"
        )
                || response
                .get("choices")
                .isEmpty()) {

            throw new RuntimeException(
                    "Groq no devolvió una respuesta válida."
            );
        }

        JsonNode choice =
                response
                        .get("choices")
                        .get(0);

        if (!choice.has(
                "message"
        )) {

            throw new RuntimeException(
                    "Groq no devolvió un mensaje válido."
            );
        }

        return choice.get(
                "message"
        );
    }

    // ==========================================================
    // FUNCTION CALLING
    // ==========================================================

    private List<Map<String, Object>> crearTools() {

        List<Map<String, Object>> tools =
                new ArrayList<>();

        // ======================================================
        // TOOL 1 - HABITACIONES
        // ======================================================

        Map<String, Object> propiedadesHabitacion =
                new LinkedHashMap<>();

        propiedadesHabitacion.put(
                "fechaInicio",

                Map.of(
                        "type",
                        "string",

                        "description",
                        """
                        Fecha de ingreso en formato YYYY-MM-DD.
                        Utilizar cuando el usuario quiera consultar
                        disponibilidad para fechas específicas.
                        """
                )
        );

        propiedadesHabitacion.put(
                "fechaFin",

                Map.of(
                        "type",
                        "string",

                        "description",
                        """
                        Fecha de salida en formato YYYY-MM-DD.
                        Utilizar cuando el usuario quiera consultar
                        disponibilidad para fechas específicas.
                        """
                )
        );

        Map<String, Object> parametrosHabitacion =
                new LinkedHashMap<>();

        parametrosHabitacion.put(
                "type",
                "object"
        );

        parametrosHabitacion.put(
                "properties",
                propiedadesHabitacion
        );

        // Las fechas son opcionales porque también
        // se puede consultar el catálogo general.

        parametrosHabitacion.put(
                "required",
                List.of()
        );

        Map<String, Object> funcionHabitaciones =
                new LinkedHashMap<>();

        funcionHabitaciones.put(
                "name",
                "consultar_habitaciones"
        );

        funcionHabitaciones.put(
                "description",
                """
                Consulta las habitaciones reales registradas
                en el Hotel Royal Suites.

                Sin fechas devuelve el catálogo general.

                Con fechaInicio y fechaFin devuelve únicamente
                habitaciones disponibles para ese período.

                Nunca debe asumirse disponibilidad de una estadía
                a partir de una consulta sin fechas.
                """
        );

        funcionHabitaciones.put(
                "parameters",
                parametrosHabitacion
        );

        Map<String, Object> habitacionToolDefinition =
                new LinkedHashMap<>();

        habitacionToolDefinition.put(
                "type",
                "function"
        );

        habitacionToolDefinition.put(
                "function",
                funcionHabitaciones
        );

        tools.add(
                habitacionToolDefinition
        );

        // ======================================================
        // TOOL 2 - SERVICIOS
        // ======================================================

        tools.add(
                Map.of(
                        "type",
                        "function",

                        "function",
                        Map.of(
                                "name",
                                "consultar_servicios_hotel",

                                "description",
                                """
                                Consulta los servicios reales
                                registrados en el Hotel Royal Suites.
                                """,

                                "parameters",
                                Map.of(
                                        "type",
                                        "object",

                                        "properties",
                                        Map.of(),

                                        "required",
                                        List.of()
                                )
                        )
                )
        );

        // ======================================================
        // TOOL 3 - RESERVA
        // ======================================================

        Map<String, Object> propiedadesReserva =
                new LinkedHashMap<>();

        propiedadesReserva.put(
                "idHabitacion",

                Map.of(
                        "type",
                        "integer",

                        "description",
                        """
                        ID real de la habitación que el usuario
                        desea reservar.
                        """
                )
        );

        propiedadesReserva.put(
                "fechaInicio",

                Map.of(
                        "type",
                        "string",

                        "description",
                        """
                        Fecha de ingreso en formato YYYY-MM-DD.
                        """
                )
        );

        propiedadesReserva.put(
                "fechaFin",

                Map.of(
                        "type",
                        "string",

                        "description",
                        """
                        Fecha de salida en formato YYYY-MM-DD.
                        """
                )
        );

        Map<String, Object> parametrosReserva =
                new LinkedHashMap<>();

        parametrosReserva.put(
                "type",
                "object"
        );

        parametrosReserva.put(
                "properties",
                propiedadesReserva
        );

        parametrosReserva.put(
                "required",
                List.of(
                        "idHabitacion",
                        "fechaInicio",
                        "fechaFin"
                )
        );

        Map<String, Object> funcionReserva =
                new LinkedHashMap<>();

        funcionReserva.put(
                "name",
                "solicitar_reserva_habitacion"
        );

        funcionReserva.put(
                "description",
                """
                Registra una solicitud de reserva de una habitación
                para el usuario actualmente autenticado.

                Solo debe utilizarse cuando se conozcan
                idHabitacion, fechaInicio y fechaFin.

                Nunca debe inventar ninguno de esos datos.
                """
        );

        funcionReserva.put(
                "parameters",
                parametrosReserva
        );

        Map<String, Object> reservaToolDefinition =
                new LinkedHashMap<>();

        reservaToolDefinition.put(
                "type",
                "function"
        );

        reservaToolDefinition.put(
                "function",
                funcionReserva
        );

        tools.add(
                reservaToolDefinition
        );

        // ======================================================
        // DEVOLVER TOOLS
        // ======================================================

        return tools;
    }
}