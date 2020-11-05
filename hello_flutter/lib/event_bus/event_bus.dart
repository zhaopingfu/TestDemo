typedef void EventCallback(args);

class EventBus {
  EventBus._internal();

  static EventBus _singleton = EventBus._internal();

  factory EventBus() => _singleton;

  Map<String, List<EventCallback>> _eventMap = new Map<String, List<EventCallback>>();

  void on(String eventName, EventCallback eventCallback) {
    if (eventName == null || eventCallback == null) {
      return;
    }
    _eventMap[eventName] ??= List<EventCallback>();
    _eventMap[eventName].add(eventCallback);
  }

  void off(String eventName, [EventCallback eventCallback]) {
    if (eventName == null || _eventMap[eventName] == null) {
      return;
    }
    if (eventCallback == null) {
      _eventMap[eventName] = null;
    } else {
      _eventMap[eventName].remove(eventCallback);
    }
  }

  void emit(String eventName, [args]) {
    if (eventName == null || _eventMap[eventName] == null || _eventMap[eventName].isEmpty) {
      return;
    }
    int length = _eventMap[eventName].length;
    for (int i = length - 1; i >= 0; --i) {
      _eventMap[eventName][i](args);
    }
  }
}

var bus = EventBus();
