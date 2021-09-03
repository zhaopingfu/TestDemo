typedef EventCallback = void Function(dynamic args);

EventBus bus = EventBus();

class EventBus {
  factory EventBus() => _singleton;

  EventBus._internal();

  static final EventBus _singleton = EventBus._internal();

  final Map<String, List<EventCallback>?> _eventMap = <String, List<EventCallback>>{};

  void on(String eventName, EventCallback eventCallback) {
    if (eventName == null || eventCallback == null) {
      return;
    }

    if (_eventMap[eventName] == null) {
      _eventMap[eventName] = <EventCallback>[];
    }
    _eventMap[eventName]?.add(eventCallback);
  }

  void off(String eventName, [EventCallback? eventCallback]) {
    if (eventName == null || _eventMap[eventName] == null) {
      return;
    }
    if (eventCallback == null) {
      _eventMap[eventName] = null;
    } else {
      _eventMap[eventName]?.remove(eventCallback);
    }
  }

  void emit(String eventName, [dynamic args]) {
    if (eventName == null || _eventMap[eventName] == null || (_eventMap[eventName]?.isEmpty ?? true)) {
      return;
    }
    final int length = _eventMap[eventName]!.length;
    for (int i = length - 1; i >= 0; --i) {
      _eventMap[eventName]![i](args);
    }
  }
}
