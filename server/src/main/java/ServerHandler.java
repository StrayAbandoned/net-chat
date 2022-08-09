import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang3.RandomStringUtils;
import ru.gb.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class ServerHandler extends ChannelInboundHandlerAdapter {

    private final Server server;
    private String login, nick;


    ServerHandler(Server server) {
        this.server = server;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Request request = (Request) msg;
        switch (request.getType()){
            case REG -> {
                if (server.getAuthentication().registration((RegRequest) msg)) {
                    ctx.writeAndFlush(new RegResponse(ResponseType.REG_OK));
                    Server.getLogger().info("Registration successful");
                } else {
                    ctx.writeAndFlush(new RegResponse(ResponseType.REG_NO));
                    Server.getLogger().info("Registration failed");
                }
            }
            case AUTH -> {
                if (server.getAuthentication().login((AuthRequest) msg)) {
                    login = server.getAuthentication().getLogin((AuthRequest) msg);
                    nick = server.getAuthentication().getNick(login);
                    server.getChannels().put(nick, ctx.channel());
                    ctx.writeAndFlush(new AuthResponse(ResponseType.AUTH_OK, nick));
                    Server.getLogger().info("Client logged in");
                } else {
                    ctx.writeAndFlush(new AuthResponse(ResponseType.AUTH_NO));
                    Server.getLogger().info("Login failed");
                }
            }
            case ADDCLIENT -> {
                if(!server.getAuthentication().getClients().contains(nick)){
                    server.getAuthentication().getClients().add(nick);
                }
                for (Channel c: server.getChannels().values()){
                    c.writeAndFlush(new BroadcastClientListResponse(server.getAuthentication().getClients()));
                }


            }
            case REMOVECLIENT -> {
                server.getAuthentication().getClients().remove(nick);
                ctx.writeAndFlush(new BroadcastClientListResponse(server.getAuthentication().getClients()));
                for (Channel c: server.getChannels().values()){
                    c.writeAndFlush(new BroadcastClientListResponse(server.getAuthentication().getClients()));
                }
            }
            case SEND_TO_EVERYONE -> {
                for (Channel c: server.getChannels().values()){

                    c.writeAndFlush(new MessageToEveryoneResponse(String.format("[%s]: %s", ((SendToEveryoneRequest) request).getNick(), ((SendToEveryoneRequest) request).getMessage())));
                }
            }
            case PRIVATE_MESSAGE ->{
                Channel c1 = server.getChannels().get(((PrivateMessageRequest) request).getReceiver());
                Channel c2 = server.getChannels().get(((PrivateMessageRequest) request).getSender());

                c1.writeAndFlush(new PrivateMessageResponse(String.format("[%s to you]: %s", ((PrivateMessageRequest) request).getSender(), ((PrivateMessageRequest) request).getMessage())));
                c2.writeAndFlush(new PrivateMessageResponse(String.format("[You to %s]: %s", ((PrivateMessageRequest) request).getReceiver(), ((PrivateMessageRequest) request).getMessage())));

            }
            case CHANGE_PASSWORD -> {
                if(server.getAuthentication().changePassword((ChangePasswordRequest) msg)){
                    ctx.writeAndFlush(new ChangePasswordResponse(ResponseType.CHANGE_PASS_OK));
                } else {
                    ctx.writeAndFlush(new ChangePasswordResponse(ResponseType.CHANGE_PASS_NO));
                }

            }
            case CHANGE_NICK -> {
                if(server.getAuthentication().changeNick((ChangeNickRequest) msg)){
                    ctx.writeAndFlush(new ChangeNickResponse(ResponseType.CHANGE_NICK_OK));
                } else {
                    ctx.writeAndFlush(new ChangeNickResponse(ResponseType.CHANGE_NICK_NO));
                }
            }

        }

    }




    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("Клиент подключился");


    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}