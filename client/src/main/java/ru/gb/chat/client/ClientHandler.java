package ru.gb.chat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import ru.gb.*;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    String nick;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Response response = (Response) msg;
        switch (response.getType()) {
            case REG_OK -> {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Registration successfully!", ButtonType.OK);
                    alert.showAndWait();
                });
            }
            case REG_NO -> {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Registration failed!", ButtonType.OK);
                    alert.showAndWait();
                });
            }
            case AUTH_OK -> {
                nick = ((AuthResponse) response).getNick();
                Aspect.mainController.setNick(((AuthResponse) response).getNick());
                ctx.writeAndFlush(new AddClientRequest(nick));
                Aspect.mainController.setAuthenticated(true);


            }
            case AUTH_NO -> {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Log in failed!", ButtonType.OK);
                    alert.showAndWait();
                });
            }
            case BROADCAST_CLIENT_LIST -> {
                Platform.runLater(() -> {
                    Aspect.mainController.setClients(((BroadcastClientListResponse) response).getClients());
                    Aspect.mainController.showClientList();
                });

            }
            case MESSAGETOEVERYONE -> {
                System.out.println(((MessageToEveryoneResponse) response).getMessage());
                Aspect.mainController.getTextArea().appendText(((MessageToEveryoneResponse) response).getMessage() + "\n");
            }
            case PRIVATE_MESSAGE -> {
                Aspect.mainController.getTextArea().appendText(((PrivateMessageResponse) response).getMessage() + "\n");
            }
            case CHANGE_PASS_NO -> {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Password wasn't changed!", ButtonType.OK);
                    alert.showAndWait();
                });
            }
            case CHANGE_PASS_OK -> {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Password was changed!", ButtonType.OK);
                    alert.showAndWait();
                });
                Aspect.mainController.setAuthenticated(false);
            }
            case CHANGE_NICK_NO -> {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Nick wasn't changed!", ButtonType.OK);
                    alert.showAndWait();
                });
            }
            case CHANGE_NICK_OK -> {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Nick was changed!", ButtonType.OK);
                    alert.showAndWait();
                });
                Aspect.mainController.setAuthenticated(false);
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
