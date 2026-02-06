# Princípio de Inversão de Dependência (DIP)

## 1. O Mau Design (Análise da Violação)

A classe `RecuperadorDeSenha` apresenta um acoplamento rígido com a classe concreta `ServicoEmail`. Isso gera diversos problemas de manutenção:

* **Dificuldade de Expansão**: Se a empresa decidir enviar recuperações por SMS ou WhatsApp, teríamos que modificar o código interno da classe `RecuperadorDeSenha`, violando também o princípio Aberto/Fechado (OCP).
* **Impossibilidade de Testes Unitários**: Não conseguimos testar a lógica de recuperação sem disparar um e-mail real (ou sem ter a classe de e-mail pronta), pois a dependência é criada internamente (`new ServicoEmail()`).
* **Dependência Invertida**: O "Alto Nível" (Regra de Negócio) está refém do "Baixo Nível" (Detalhe de Infraestrutura).

## 2. A Solução (Refatoração)

Para resolver o problema, criamos uma camada de abstração. Agora, tanto o serviço de e-mail quanto o de senha dependem de uma Interface.

### A Abstração (Contrato)
```java
public interface NotificationService {
    void enviarMensagem(String mensagem);
}
```

### Implementações (Detalhes)
```java
public class ServicoEmail implements NotificationService {
    @Override
    public void enviarMensagem(String mensagem) {
        System.out.println("Enviando E-mail SMTP: " + mensagem);
    }
}

public class ServicoSMS implements NotificationService {
    @Override
    public void enviarMensagem(String mensagem) {
        System.out.println("Enviando SMS Gateway: " + mensagem);
    }
}
```

### Classe de Alto Nível Refatorada

Observe que não existe mais o operador `new` dentro desta classe.
```java
public class RecuperadorDeSenha {
    private final NotificationService notificationService;

    // Injeção de Dependência via Construtor
    public RecuperadorDeSenha(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void recuperar(String email) {
        String link = "http://techstore.com/reset?token=123";
        notificationService.enviarMensagem("Clique no link: " + link);
    }
}
```

## 3. Demonstração de Flexibilidade

O impacto positivo é visto na classe `Main`. Podemos trocar a estratégia de comunicação sem alterar a classe `RecuperadorDeSenha`.
```java
public class Main {
    public static void main(String[] args) {
        // Exemplo 1: Recuperação via E-mail
        NotificationService email = new ServicoEmail();
        RecuperadorDeSenha recEmail = new RecuperadorDeSenha(email);
        recEmail.recuperar("usuario@tech.com");

        // Exemplo 2: Recuperação via SMS (Mesma classe, comportamento diferente)
        NotificationService sms = new ServicoSMS();
        RecuperadorDeSenha recSMS = new RecuperadorDeSenha(sms);
        recSMS.recuperar("4799999-9999");
    }
}
```

**Resultado**: A classe `RecuperadorDeSenha` tornou-se resiliente a mudanças. Ela não precisa saber como a mensagem é enviada, apenas que o objeto recebido sabe `enviarMensagem()`.
