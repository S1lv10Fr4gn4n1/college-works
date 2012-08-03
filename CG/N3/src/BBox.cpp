#include "BBox.h"
#include "Transform.h"

// constructor
// inicializacoes necessarias
BBox::BBox() {
}

BBox::~BBox() {
}

void BBox::init() {
	xMax = yMax = zMax = -1000000;
	xMin = yMin = zMin =  1000000;
}

// metodo responsavel por identificar se o ponto passado, esta
// entre os valores minimo e maximo do bbox
bool BBox::inBBox(float _x, float _y, float _z){
	if ((_x < xMax && _x > xMin) &&
		(_y < yMax && _x > yMin)) {
		return true;
	}

	return false;
}

// metodo responsavel por identificar se o ponto passado, esta
// entre os valores minimo e maximo do bbox
bool BBox::inBBox(Point* _point){
	if ((_point->getX() < xMax && _point->getX() > xMin) &&
		(_point->getY() < yMax && _point->getY() > yMin)) {
		return true;
	}

	return false;
}

float BBox::getXMin() {
	return xMin;
}

void BBox::setXMin(float _x) {
	xMin = _x;
}

float BBox::getXMax() {
	return xMax;
}

void BBox::setXMax(float _x) {
	xMax = _x;
}

float BBox::getYMin() {
	return yMin;
}

void BBox::setYMin(float _y) {
	yMin = _y;
}

float BBox::getYMax() {
	return yMax;
}

void BBox::setYMax(float _y) {
	yMax = _y;
}

float BBox::getZMin() {
	return zMin;
}

void BBox::setZMin(float _z) {
	zMin = _z;
}

float BBox::getZMax() {
	return zMax;
}

void BBox::setZMax(float _z) {
	zMax = _z;
}
