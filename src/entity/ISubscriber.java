package entity;

public interface ISubscriber {
  String getId();
  void consume(Message message);

}
