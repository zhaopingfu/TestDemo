import 'dart:math';
import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:hello_flutter/sample/turn_box_sample.dart';

class GradientCircularProgressSampleRoute extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _GradientCircularProgressSampleRouteState();
}

class _GradientCircularProgressSampleRouteState extends State<GradientCircularProgressSampleRoute> with TickerProviderStateMixin {
  AnimationController _animationController;

  @override
  void initState() {
    super.initState();

    _animationController = AnimationController(vsync: this, duration: Duration(seconds: 3));
    bool isForward = true;
    _animationController.addStatusListener((status) {
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
    _animationController?.dispose();
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
              builder: (BuildContext context, Widget child) {
                return Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  mainAxisSize: MainAxisSize.max,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Wrap(spacing: 15, runSpacing: 15, children: [
                      GradientCircularProgressSample(
                        radius: 50.0,
                        colors: [Colors.blue, Colors.blue],
                        strokeWidth: 3.0,
                        value: _animationController.value,
                      ),
                      GradientCircularProgressSample(
                        colors: [Colors.red, Colors.orange],
                        radius: 50.0,
                        strokeWidth: 3.0,
                        value: _animationController.value,
                      ),
                      GradientCircularProgressSample(
                        colors: [Colors.red, Colors.orange, Colors.red],
                        radius: 50.0,
                        strokeWidth: 5.0,
                        value: _animationController.value,
                      ),
                      GradientCircularProgressSample(
                        colors: [Colors.teal, Colors.cyan],
                        strokeWidth: 5.0,
                        radius: 50,
                        value: CurvedAnimation(parent: _animationController, curve: Curves.ease).value,
                      ),
                      TurnBoxDemo(
                        turns: 1 / 8,
                        child: GradientCircularProgressSample(
                            colors: [Colors.red, Colors.orange, Colors.red],
                            radius: 50.0,
                            strokeWidth: 5.0,
                            backgroundColor: Colors.red[50],
                            total: 1.5 * pi,
                            value: CurvedAnimation(parent: _animationController, curve: Curves.ease).value),
                      ),
                      RotatedBox(
                        quarterTurns: 2,
                        child: GradientCircularProgressSample(
                            colors: [Colors.blue[700], Colors.blue[200]],
                            radius: 50.0,
                            strokeWidth: 3.0,
                            backgroundColor: Colors.transparent,
                            value: _animationController.value),
                      ),
                      GradientCircularProgressSample(
                        colors: [Colors.red, Colors.amber, Colors.cyan, Colors.green[200], Colors.blue, Colors.red],
                        radius: 50.0,
                        strokeWidth: 5.0,
                        value: _animationController.value,
                      )
                    ]),
                    GradientCircularProgressSample(
                      colors: [Colors.blue[700], Colors.blue[200]],
                      radius: 100.0,
                      strokeWidth: 20.0,
                      value: _animationController.value,
                    ),
                    Padding(
                      padding: const EdgeInsets.symmetric(vertical: 16.0),
                      child: GradientCircularProgressSample(
                        colors: [Colors.blue[700], Colors.blue[300]],
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
                          padding: EdgeInsets.only(bottom: 8),
                          child: TurnBoxDemo(
                            turns: .75,
                            child: GradientCircularProgressSample(
                              colors: [Colors.teal, Colors.cyan[500]],
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
                        children: [
                          Positioned(
                            top: .0,
                            height: 200.0,
                            child: TurnBoxDemo(
                              turns: .75,
                              child: GradientCircularProgressSample(
                                colors: [Colors.teal, Colors.cyan[500]],
                                radius: 100.0,
                                strokeWidth: 8.0,
                                value: _animationController.value,
                                total: pi,
                              ),
                            ),
                          ),
                          Padding(
                            padding: EdgeInsets.only(top: 10.0),
                            child: Text(
                              "${(_animationController.value * 100).toInt()}%",
                              style: TextStyle(
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
    Key key,
    this.radius,
    this.strokeWidth = 2.0,
    this.value,
    this.total = 2 * pi,
    this.backgroundColor = const Color(0xFFEEEEEE),
    @required this.colors,
    this.stops,
  }) : super(key: key);

  final double radius;
  final double strokeWidth;
  final double value;
  final double total;
  final Color backgroundColor;
  final List<Color> colors;
  final List<double> stops;

  @override
  Widget build(BuildContext context) {
    var _colors = colors;
    if (_colors == null) {
      var color = Theme.of(context).accentColor;
      _colors = [color, color];
    }
    return Transform.rotate(
      angle: -pi / 2.0,
      child: CustomPaint(
        size: Size.fromRadius(radius),
        painter: GradientCircularProgressPaintSample(
          radius: this.radius,
          strokeWidth: this.strokeWidth,
          value: this.value,
          total: this.total,
          backgroundColor: this.backgroundColor,
          colors: _colors,
          stops: this.stops,
        ),
      ),
    );
  }
}

class GradientCircularProgressPaintSample extends CustomPainter {
  const GradientCircularProgressPaintSample({
    Key key,
    this.radius,
    this.strokeWidth = 10.0,
    this.value,
    this.total = 2 * pi,
    this.backgroundColor = const Color(0xFFEEEEEE),
    @required this.colors,
    this.stops,
  });

  final double radius;
  final double strokeWidth;
  final double value;
  final double total;
  final Color backgroundColor;
  final List<Color> colors;
  final List<double> stops;

  @override
  void paint(Canvas canvas, Size size) {
    if (radius != null) {
      size = Size.fromRadius(radius);
    }

    var _start = .0;

    var _offset = strokeWidth / 2.0;
    var rect = Offset(_offset, _offset) & Size(size.width - strokeWidth, size.height - strokeWidth);

    double _value = (value ?? .0);
    _value = _value.clamp(.0, 1.0) * total;

    var paint = Paint()
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
      paint.shader = SweepGradient(startAngle: _start, endAngle: _value, colors: colors, stops: stops).createShader(rect);
      canvas.drawArc(rect, _start, _value, false, paint);
    }
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => true;
}
