package br.com.alura.desafiomusicas.desafiomusicas.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.service.OpenAiService;

import java.util.List;

public class ConsultaChatGPT {
//código do chatGPT que também não funciona!
    public static String obterDadosArtista(String texto) {
        // Certifique-se de que a variável de ambiente OPENAI_APIKEY está configurada corretamente
        String apiKey = System.getenv("OPENAI_APIKEY");
        if (apiKey == null) {
            throw new IllegalArgumentException("API Key não configurada.");
        }

        OpenAiService service = new OpenAiService(apiKey);

        // Criar a requisição de conclusão
        CompletionRequest requisicao = CompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .prompt("Me fale sobre o artista: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        // Enviar a requisição e obter a resposta
        List<CompletionChoice> choices = service.createCompletion(requisicao).getChoices();
        if (choices.isEmpty()) {
            return "Nenhuma resposta encontrada.";
        }

        // Retornar o texto da primeira escolha
        return choices.get(0).getText().trim();
    }
}
