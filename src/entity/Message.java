package entity;

public class Message {

  private final String msg;

  private final String msgId;

  public Message(String msg, String msgId) {
    this.msg = msg;
    this.msgId = msgId;
  }
  public String getMsg() {
    return this.msg ;
  }
}
