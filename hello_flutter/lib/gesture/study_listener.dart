import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class StudyListener extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => StudyListenerState();
}

class StudyListenerState extends State<StudyListener> {
  PointerEvent _event;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Container(
          width: double.infinity,
          child: SingleChildScrollView(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Listener(
                  child: Container(
                    alignment: Alignment.center,
                    color: Colors.blueAccent,
                    width: 300.0,
                    height: 150.0,
                    child: Text(
                      "dx: ${_event?.position?.dx ?? ''}\ndy: ${_event?.position?.dy ?? ''}",
                      style: TextStyle(color: Colors.white),
                    ),
                  ),
                  onPointerDown: (PointerDownEvent event) => setState(() => _event = event),
                  onPointerMove: (PointerMoveEvent event) => setState(() => _event = event),
                  onPointerUp: (PointerUpEvent event) => setState(() => _event = event),
                ),
                Listener(
                  child: ConstrainedBox(
                    constraints: BoxConstraints.tight(Size(300, 150)),
                    child: Center(
                      child: Text('Box A'),
                    ),
                  ),
                  behavior: HitTestBehavior.opaque,
                  onPointerDown: (event) {
                    print('down A');
                  },
                ),
                Stack(
                  children: [
                    Listener(
                      child: ConstrainedBox(
                        constraints: BoxConstraints.tight(Size(300, 200)),
                        child: DecoratedBox(
                          decoration: BoxDecoration(color: Colors.blue),
                        ),
                      ),
                      onPointerDown: (event) => print('down 0'),
                    ),
                    Listener(
                      child: ConstrainedBox(
                        constraints: BoxConstraints.tight(Size(200, 100)),
                        child: Center(
                          child: Text('左上角200*100范围内非文本区域点击'),
                        ),
                      ),
                      behavior: HitTestBehavior.opaque,
                      onPointerDown: (event) => print('down 1'),
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
                      onPointerDown: (event) => print('in'),
                    ),
                  ),
                  onPointerDown: (event) => print('out'),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
