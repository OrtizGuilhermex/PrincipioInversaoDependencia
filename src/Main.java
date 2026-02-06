
public class Main {
    public static void main(String[] args) {
        NotificationService emailNotification = new ServicoEmail();
        RecuperadorDeSenha recuperadorDeSenha = new RecuperadorDeSenha(emailNotification);
        recuperadorDeSenha.recuperar("");

        NotificationService SMSNotification = new ServicoSMS();
        recuperadorDeSenha = new RecuperadorDeSenha(SMSNotification);
        recuperadorDeSenha.recuperar("");

    }
}