import 'package:flutter/cupertino.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';

class StudyGestureRecognizer extends StatefulWidget {
  const StudyGestureRecognizer({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _StudyGestureRecognizerState();
}

class _StudyGestureRecognizerState extends State<StudyGestureRecognizer> {
  bool _toggle = false;
  final TapGestureRecognizer _tapGestureRecognizer = TapGestureRecognizer();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SizedBox(
        width: double.infinity,
        height: double.infinity,
        child: SafeArea(
          child: Center(
            child: Text.rich(
              TextSpan(
                children: <InlineSpan>[
                  const TextSpan(text: '你好世界'),
                  TextSpan(
                    text: '点我变色',
                    style: TextStyle(
                      fontSize: 30.0,
                      color: _toggle ? Colors.blue : Colors.red,
                    ),
                    recognizer: _tapGestureRecognizer
                      ..onTap = () {
                        setState(() {
                          _toggle = !_toggle;
                        });
                      },
                  ),
                  const TextSpan(text: '你好世界'),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }

  @override
  void dispose() {
    _tapGestureRecognizer.dispose();
    super.dispose();
  }
}
