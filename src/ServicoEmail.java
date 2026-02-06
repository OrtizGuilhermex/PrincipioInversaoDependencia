public class ServicoEmail implements NotificationService  {

    @Override
    public void enviarMensagem(String mensagem) {
        System.out.println("Enviando E-mail SMTP:" + mensagem);
    }
}