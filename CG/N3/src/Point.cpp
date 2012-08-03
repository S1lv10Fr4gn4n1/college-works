#include "Point.h"

Point::Point() {
}

Point::Point(float _x, float _y, float _z, float _w) {
	x = _x;
	y = _y;
	z = _z;
	w = _w;
}

Point::Point(float _x, float _y) {
	x = _x;
	y = _y;
	z = 0.0f;
	w = 1.1f;
}

Point::~Point() {
}

float Point::getX() {
	return x;
}

void Point::setX(float _x) {
	x = _x;
}

float Point::getY() {
	return y;
}

void Point::setY(float _y) {
	y = _y;
}

float Point::getZ() {
	return z;
}

void Point::setZ(float _z) {
	z = _z;
}

float Point::getW() {
	return w;
}

void Point::setW(float _w) {
	w = _w;
}
