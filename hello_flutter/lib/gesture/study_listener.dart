import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class StudyListener extends StatefulWidget {
  const StudyListener({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => StudyListenerState();
}

class StudyListenerState extends State<StudyListener> {
  PointerEvent? _event;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: SizedBox(
          width: double.infinity,
          child: SingleChildScrollView(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                Listener(
                  child: Container(
                    alignment: Alignment.center,
                    color: Colors.blueAccent,
                    width: 300.0,
                    height: 150.0,
                    child: Text(
                      "dx: ${_event?.position.dx ?? ''}\ndy: ${_event?.position.dy ?? ''}",
                      style: const TextStyle(color: Colors.white),
                    ),
                  ),
                  onPointerDown: (PointerDownEvent event) => setState(() => _event = event),
                  onPointerMove: (PointerMoveEvent event) => setState(() => _event = event),
                  onPointerUp: (PointerUpEvent event) => setState(() => _event = event),
                ),
                Listener(
                  child: ConstrainedBox(
                    constraints: BoxConstraints.tight(const Size(300, 150)),
                    child: const Center(
                      child: Text('Box A'),
                    ),
                  ),
                  behavior: HitTestBehavior.opaque,
                  onPointerDown: (PointerDownEvent event) {
                    print('down A');
                  },
                ),
                Stack(
                  children: <Widget>[
                    Listener(
                      child: ConstrainedBox(
                        constraints: BoxConstraints.tight(const Size(300, 200)),
                        child: const DecoratedBox(
                          decoration: BoxDecoration(color: Colors.blue),
                        ),
                      ),
                      onPointerDown: (PointerDownEvent event) => print('down 0'),
                    ),
                    Listener(
                      child: ConstrainedBox(
                        constraints: BoxConstraints.tight(const Size(200, 100)),
                        child: const Center(
                          child: Text('左上角200*100范围内非文本区域点击'),
                        ),
                      ),
                      behavior: HitTestBehavior.opaque,
                      onPointerDown: (PointerDownEvent event) => print('down 1'),
                    ),
                  ],
                ),
                Listener(
                  child: AbsorbPointer(
                    child: Listener(
                      child: Container(
                        color: Colors.red,
                        width: 200.0,
                        height: 100.0,
                      ),
                      onPointerDown: (PointerDownEvent event) => print('in'),
                    ),
                  ),
                  onPointerDown: (PointerDownEvent event) => print('out'),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
