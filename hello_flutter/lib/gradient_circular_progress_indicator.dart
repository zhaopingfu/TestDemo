import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:hello_flutter/turn_box.dart';

class GradientCircularProgressIndicator extends StatelessWidget {
  const GradientCircularProgressIndicator({
    Key? key,
    this.strokeWidth = 2.0,
    required this.radius,
    required this.colors,
    this.strokeCapRound = false,
    this.backgroundColor = const Color(0xFFEEEEEE),
    this.totalAngle = 2 * pi,
    this.value = 0.0,
  }) : super(key: key);

  /// 粗细
  final double strokeWidth;

  /// 圆的半径
  final double radius;

  /// 渐变色数组
  final List<Color> colors;

  /// 两端是否为圆角
  final bool strokeCapRound;

  /// 进度条背景色
  final Color backgroundColor;

  /// 进度条的总弧度，2*PI为整圆，小于2*PI则不是整圆
  final double totalAngle;

  /// 当前进度，取值范围 [0.0-1.0]
  final double value;

  @override
  Widget build(BuildContext context) {
    double _offset = .0;
    if (strokeCapRound) {
      _offset = asin(strokeWidth / (radius * 2 - strokeWidth));
    }
    List<Color> _colors = colors;
    if (_colors == null) {
      final Color color = Theme.of(context).accentColor;
      _colors = <Color>[color, color];
    }

    return Transform.rotate(
      angle: -pi / 2.0 - _offset,
      child: CustomPaint(
        size: Size.fromRadius(radius),
        painter: _GradientCircularProgressPainter(
          strokeWidth: strokeWidth,
          strokeCapRound: strokeCapRound,
          backgroundColor: backgroundColor,
          radius: radius,
          total: totalAngle,
          colors: _colors,
          value: value,
        ),
      ),
    );
  }
}

class _GradientCircularProgressPainter extends CustomPainter {
  const _GradientCircularProgressPainter({
    this.strokeWidth = 10.0,
    this.strokeCapRound = false,
    this.backgroundColor = const Color(0xFFEEEEEE),
    required this.radius,
    this.total = 2 * pi,
    required this.colors,
    this.value = 0.0,
  });

  final double strokeWidth;
  final bool strokeCapRound;
  final double value;
  final Color backgroundColor;
  final List<Color> colors;
  final double total;
  final double radius;

  @override
  void paint(Canvas canvas, Size size) {
    if (radius != null) {
      size = Size.fromRadius(radius);
    }
    final double _offset = strokeWidth / 2.0;
    double _value = value;
    _value = _value.clamp(.0, 1.0).toDouble() * total;
    double _start = .0;

    if (strokeCapRound) {
      _start = asin(strokeWidth / (size.width - strokeWidth));
    }

    final Rect rect = Offset(_offset, _offset) & Size(size.width - strokeWidth, size.height - strokeWidth);

    final Paint paint = Paint()
      ..strokeCap = strokeCapRound ? StrokeCap.round : StrokeCap.butt
      ..style = PaintingStyle.stroke
      ..isAntiAlias = true
      ..strokeWidth = strokeWidth;

    // 画背景
    if (backgroundColor != Colors.transparent) {
      paint.color = backgroundColor;
      canvas.drawArc(rect, _start, total, false, paint);
    }

    // 画前景，应用渐变
    if (_value > 0) {
      paint.shader = SweepGradient(
        startAngle: .0,
        endAngle: _value,
        colors: colors,
      ).createShader(rect);

      canvas.drawArc(rect, _start, _value, false, paint);
    }
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => true;
}

class GradientCircularProgressRoute extends StatefulWidget {
  const GradientCircularProgressRoute({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _GradientCircularProgressRouteState();
}

class _GradientCircularProgressRouteState extends State<GradientCircularProgressRoute> with TickerProviderStateMixin {
  late AnimationController _animationController;

  @override
  void initState() {
    super.initState();
    _animationController = AnimationController(vsync: this, duration: const Duration(seconds: 3));
    bool isForward = true;
    _animationController.addStatusListener((AnimationStatus status) {
      if (status == AnimationStatus.forward) {
        isForward = true;
      } else if (status == AnimationStatus.completed || status == AnimationStatus.dismissed) {
        if (isForward) {
          _animationController.reverse();
        } else {
          _animationController.forward();
        }
      } else if (status == AnimationStatus.reverse) {
        isForward = false;
      }
    });
    _animationController.forward();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: SingleChildScrollView(
          child: Center(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                AnimatedBuilder(
                  animation: _animationController,
                  builder: (BuildContext context, Widget? child) {
                    return Padding(
                      padding: const EdgeInsets.symmetric(vertical: 16.0),
                      child: Column(
                        children: <Widget>[
                          Wrap(
                            spacing: 10.0,
                            runSpacing: 16.0,
                            children: <Widget>[
                              GradientCircularProgressIndicator(
                                radius: 50.0,
                                colors: const <Color>[Colors.blue, Colors.blue],
                                strokeWidth: 3.0,
                                value: _animationController.value,
                              ),
                              GradientCircularProgressIndicator(
                                colors: const <Color>[Colors.red, Colors.orange],
                                radius: 50.0,
                                strokeWidth: 3.0,
                                value: _animationController.value,
                              ),
                              GradientCircularProgressIndicator(
                                colors: const <Color>[Colors.red, Colors.orange, Colors.red],
                                radius: 50.0,
                                strokeWidth: 5.0,
                                value: _animationController.value,
                              ),
                              GradientCircularProgressIndicator(
                                colors: const <Color>[Colors.teal, Colors.cyan],
                                radius: 50.0,
                                strokeWidth: 5.0,
                                strokeCapRound: true,
                                value: CurvedAnimation(parent: _animationController, curve: Curves.decelerate).value,
                              ),
                              TurnBox(
                                turns: 1 / 8,
                                child: GradientCircularProgressIndicator(
                                    colors: const <Color>[Colors.red, Colors.orange, Colors.red],
                                    radius: 50.0,
                                    strokeWidth: 5.0,
                                    strokeCapRound: true,
                                    backgroundColor: Colors.red[50] ?? Colors.red,
                                    totalAngle: 1.5 * pi,
                                    value: CurvedAnimation(parent: _animationController, curve: Curves.ease).value),
                              ),
                              RotatedBox(
                                quarterTurns: 1,
                                child: GradientCircularProgressIndicator(
                                    colors: <Color>[Colors.blue[700] ?? Colors.blue, Colors.blue[200] ?? Colors.blue],
                                    radius: 50.0,
                                    strokeWidth: 3.0,
                                    strokeCapRound: true,
                                    backgroundColor: Colors.transparent,
                                    value: _animationController.value),
                              ),
                              GradientCircularProgressIndicator(
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
                                strokeCapRound: true,
                                value: _animationController.value,
                              ),
                            ],
                          ),
                          GradientCircularProgressIndicator(
                            colors: <Color>[Colors.blue[700] ?? Colors.blue, Colors.blue[200] ?? Colors.blue],
                            radius: 100.0,
                            strokeWidth: 20.0,
                            value: _animationController.value,
                          ),
                          Padding(
                            padding: const EdgeInsets.symmetric(vertical: 16.0),
                            child: GradientCircularProgressIndicator(
                              colors: <Color>[Colors.blue[700] ?? Colors.blue, Colors.blue[300] ?? Colors.blue],
                              radius: 100.0,
                              strokeWidth: 20.0,
                              value: _animationController.value,
                              strokeCapRound: true,
                            ),
                          ),
                          ClipRect(
                            child: Align(
                              alignment: Alignment.topCenter,
                              heightFactor: .5,
                              child: Padding(
                                padding: const EdgeInsets.only(bottom: 8.0),
                                child: SizedBox(
                                  // width: 100.0,
                                  child: TurnBox(
                                    turns: .75,
                                    child: GradientCircularProgressIndicator(
                                      colors: <Color>[Colors.teal, Colors.cyan[500] ?? Colors.cyan],
                                      radius: 100.0,
                                      strokeWidth: 8.0,
                                      value: _animationController.value,
                                      totalAngle: pi,
                                      strokeCapRound: true,
                                    ),
                                  ),
                                ),
                              ),
                            ),
                          ),
                          SizedBox(
                            height: 104.0,
                            width: 200.0,
                            child: Stack(
                              alignment: Alignment.center,
                              children: <Widget>[
                                Positioned(
                                  height: 200.0,
                                  top: .0,
                                  child: TurnBox(
                                    turns: .75,
                                    child: GradientCircularProgressIndicator(
                                      colors: <Color>[Colors.teal, Colors.cyan[500] ?? Colors.cyan],
                                      radius: 100.0,
                                      strokeWidth: 8.0,
                                      value: _animationController.value,
                                      totalAngle: pi,
                                      strokeCapRound: true,
                                    ),
                                  ),
                                ),
                                Padding(
                                  padding: const EdgeInsets.only(top: 10.0),
                                  child: Text(
                                    '${(_animationController.value * 100).toInt()}%',
                                    style: const TextStyle(
                                      fontSize: 25.0,
                                      color: Colors.blueGrey,
                                    ),
                                  ),
                                )
                              ],
                            ),
                          ),
                        ],
                      ),
                    );
                  },
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  @override
  void dispose() {
    _animationController.dispose();
    super.dispose();
  }
}
