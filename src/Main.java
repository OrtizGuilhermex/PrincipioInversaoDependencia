
public class Main {
    public static void main(String[] args) {
        NotificationService emailNotification = new ServicoEmail();
        RecuperadorDeSenha recuperadorEmail= new RecuperadorDeSenha(emailNotification);
        recuperadorEmail.recuperar("usuario@tech.com");

        NotificationService SMSNotification = new ServicoSMS();
        RecuperadorDeSenha recuperadorSMS = new RecuperadorDeSenha(SMSNotification);
        recuperadorSMS.recuperar("4799999-9999");

    }
}