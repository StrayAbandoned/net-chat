module ru.gb.chat.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.netty.transport;
    requires io.netty.codec;
    requires serviceMsg;


    opens ru.gb.chat.client to javafx.fxml;
    exports ru.gb.chat.client;
}