public class RecuperadorDeSenha  {

    private final NotificationService notificationService;

    public RecuperadorDeSenha(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    public void recuperar(String mensagem) {
        String link = "http: /techstore.com/reset?token=123";
        notificationService.enviarMensagem("Clique no link para resetar sua senha: " + link);
    }
}
