import 'dart:math';
import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:hello_flutter/sample/turn_box_sample.dart';

class GradientCircularProgressSampleRoute extends StatefulWidget {
  const GradientCircularProgressSampleRoute({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _GradientCircularProgressSampleRouteState();
}

class _GradientCircularProgressSampleRouteState extends State<GradientCircularProgressSampleRoute>
    with TickerProviderStateMixin {
  late AnimationController _animationController;

  @override
  void initState() {
    super.initState();

    _animationController = AnimationController(vsync: this, duration: const Duration(seconds: 3));
    bool isForward = true;
    _animationController.addStatusListener((AnimationStatus status) {
      if (status == AnimationStatus.forward) {
        isForward = true;
      } else if (status == AnimationStatus.reverse) {
        isForward = false;
      } else if (status == AnimationStatus.completed || status == AnimationStatus.dismissed) {
        if (isForward) {
          _animationController.reverse();
        } else {
          _animationController.forward();
        }
      }
    });
    _animationController.forward();
  }

  @override
  void dispose() {
    _animationController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 16),
            child: AnimatedBuilder(
              animation: _animationController,
              builder: (BuildContext context, Widget? child) {
                return Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  mainAxisSize: MainAxisSize.max,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    Wrap(
                      spacing: 15,
                      runSpacing: 15,
                      children: <Widget>[
                        GradientCircularProgressSample(
                          radius: 50.0,
                          colors: const <Color>[Colors.blue, Colors.blue],
                          strokeWidth: 3.0,
                          value: _animationController.value,
                        ),
                        GradientCircularProgressSample(
                          colors: const <Color>[Colors.red, Colors.orange],
                          radius: 50.0,
                          strokeWidth: 3.0,
                          value: _animationController.value,
                        ),
                        GradientCircularProgressSample(
                          colors: const <Color>[Colors.red, Colors.orange, Colors.red],
                          radius: 50.0,
                          strokeWidth: 5.0,
                          value: _animationController.value,
                        ),
                        GradientCircularProgressSample(
                          colors: const <Color>[Colors.teal, Colors.cyan],
                          strokeWidth: 5.0,
                          radius: 50,
                          value: CurvedAnimation(parent: _animationController, curve: Curves.ease).value,
                        ),
                        TurnBoxDemo(
                          turns: 1 / 8,
                          child: GradientCircularProgressSample(
                              colors: const <Color>[Colors.red, Colors.orange, Colors.red],
                              radius: 50.0,
                              strokeWidth: 5.0,
                              backgroundColor: Colors.red[50] ?? Colors.red,
                              total: 1.5 * pi,
                              value: CurvedAnimation(parent: _animationController, curve: Curves.ease).value),
                        ),
                        RotatedBox(
                          quarterTurns: 2,
                          child: GradientCircularProgressSample(
                              colors: <Color>[Colors.blue[700] ?? Colors.blue, Colors.blue[200] ?? Colors.blue],
                              radius: 50.0,
                              strokeWidth: 3.0,
                              backgroundColor: Colors.transparent,
                              value: _animationController.value),
                        ),
                        GradientCircularProgressSample(
                          colors: <Color>[
                            Colors.red,
                            Colors.amber,
                            Colors.cyan,
                            Colors.green[200] ?? Colors.green,
                            Colors.blue,
                            Colors.red
                          ],
                          radius: 50.0,
                          strokeWidth: 5.0,
                          value: _animationController.value,
                        )
                      ],
                    ),
                    GradientCircularProgressSample(
                      colors: <Color>[Colors.blue[700] ?? Colors.blue, Colors.blue[200] ?? Colors.blue],
                      radius: 100.0,
                      strokeWidth: 20.0,
                      value: _animationController.value,
                    ),
                    Padding(
                      padding: const EdgeInsets.symmetric(vertical: 16.0),
                      child: GradientCircularProgressSample(
                        colors: <Color>[Colors.blue[700] ?? Colors.blue, Colors.blue[300] ?? Colors.blue],
                        radius: 100.0,
                        strokeWidth: 20.0,
                        value: _animationController.value,
                      ),
                    ),
                    ClipRect(
                      child: Align(
                        alignment: Alignment.topCenter,
                        heightFactor: .5,
                        child: Padding(
                          padding: const EdgeInsets.only(bottom: 8),
                          child: TurnBoxDemo(
                            turns: .75,
                            child: GradientCircularProgressSample(
                              colors: <Color>[Colors.teal, Colors.cyan[500] ?? Colors.cyan],
                              radius: 100.0,
                              strokeWidth: 8.0,
                              value: _animationController.value,
                              total: pi,
                            ),
                          ),
                        ),
                      ),
                    ),
                    SizedBox(
                      width: 200.0,
                      height: 104.0,
                      child: Stack(
                        alignment: Alignment.center,
                        children: <Widget>[
                          Positioned(
                            top: .0,
                            height: 200.0,
                            child: TurnBoxDemo(
                              turns: .75,
                              child: GradientCircularProgressSample(
                                colors: <Color>[Colors.teal, Colors.cyan[500] ?? Colors.cyan],
                                radius: 100.0,
                                strokeWidth: 8.0,
                                value: _animationController.value,
                                total: pi,
                              ),
                            ),
                          ),
                          Padding(
                            padding: const EdgeInsets.only(top: 10.0),
                            child: Text(
                              '${(_animationController.value * 100).toInt()}%',
                              style: const TextStyle(
                                fontSize: 25,
                                color: Colors.blueGrey,
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ],
                );
              },
            ),
          ),
        ),
      ),
    );
  }
}

class GradientCircularProgressSample extends StatelessWidget {
  const GradientCircularProgressSample({
    Key? key,
    required this.radius,
    this.strokeWidth = 2.0,
    this.value = 0.0,
    this.total = 2 * pi,
    this.backgroundColor = const Color(0xFFEEEEEE),
    required this.colors,
  }) : super(key: key);

  final double radius;
  final double strokeWidth;
  final double value;
  final double total;
  final Color backgroundColor;
  final List<Color> colors;

  @override
  Widget build(BuildContext context) {
    List<Color> _colors = colors;
    if (_colors == null) {
      final Color color = Theme.of(context).accentColor;
      _colors = <Color>[color, color];
    }
    return Transform.rotate(
      angle: -pi / 2.0,
      child: CustomPaint(
        size: Size.fromRadius(radius),
        painter: GradientCircularProgressPaintSample(
          radius: radius,
          strokeWidth: strokeWidth,
          value: value,
          total: total,
          backgroundColor: backgroundColor,
          colors: _colors,
        ),
      ),
    );
  }
}

class GradientCircularProgressPaintSample extends CustomPainter {
  const GradientCircularProgressPaintSample({
    required this.radius,
    this.strokeWidth = 10.0,
    this.value = 0.0,
    this.total = 2 * pi,
    this.backgroundColor = const Color(0xFFEEEEEE),
    required this.colors,
  });

  final double radius;
  final double strokeWidth;
  final double value;
  final double total;
  final Color backgroundColor;
  final List<Color> colors;

  @override
  void paint(Canvas canvas, Size size) {
    if (radius != null) {
      size = Size.fromRadius(radius);
    }

    const double _start = .0;

    final double _offset = strokeWidth / 2.0;
    final Rect rect = Offset(_offset, _offset) & Size(size.width - strokeWidth, size.height - strokeWidth);

    double _value = value;
    _value = _value.clamp(.0, 1.0).toDouble() * total;

    final Paint paint = Paint()
      ..strokeWidth = strokeWidth
      ..isAntiAlias = true
      ..style = PaintingStyle.stroke;

    // 画背景
    if (backgroundColor != Colors.transparent) {
      paint.color = backgroundColor;
      canvas.drawArc(rect, _start, total, false, paint);
    }

    // 前景
    if (_value > 0) {
      paint.shader = SweepGradient(startAngle: _start, endAngle: _value, colors: colors).createShader(rect);
      canvas.drawArc(rect, _start, _value, false, paint);
    }
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => true;
}
