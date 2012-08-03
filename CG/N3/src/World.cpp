#include "World.h"
#include <vector>
#include <iostream>

using namespace std;

World::World() {
	incLeft   = 5;
	incRight  = 5;
	incTop    = 5;
	incBottom = 5;

	width = height =  500;
	left  = bottom = -250;
	top   = right  =  250;
}

World::~World() {
}

vector<ObjectGraph*> World::getObjsGragh() {
	return objsGraph;
}

void World::addObjsGraph(ObjectGraph* obj) {
	objsGraph.push_back(obj);
}

int World::getWidth() {
	return width;
}

void World::setWidth(int _width) {
	width = _width;
}

int World::getHeight() {
	return height;
}

void World::setHeight(int _height) {
	height = _height;
}

double World::getLeft() {
	return left;
}

void World::setLeft(double _left) {
	left = _left;
}

double World::getRight() {
	return right;
}

void World::setRight(double _right) {
	right = _right;
}

double World::getTop() {
	return top;
}

void World::setTop(double _top) {
	top = _top;
}

double World::getBottom() {
	return bottom;
}

void World::setBottom(double _bottom) {
	bottom = _bottom;
}

float World::getIncLeft() {
	return incLeft;
}

void World::setIncLeft(float _incLeft) {
	incLeft = _incLeft;
}

float World::getIncRight() {
	return incRight;
}

void World::setIncRight(float _incRight) {
	incRight = _incRight;
}

float World::getIncTop() {
	return incTop;
}

void World::setIncTop(float _incTop) {
	incTop = _incTop;
}

float World::getIncBottom() {
	return incBottom;
}

void World::setIncBottom(float _incBottom) {
	incBottom = _incBottom;
}

void World::northPAN() {
	top    -= 5;
	bottom -= 5;
}

void World::westPAN() {
	left  += 5;
	right += 5;
}

void World::southPAN() {
	top    += 5;
	bottom += 5;
}

void World::eastPAN() {
	left  -= 5;
	right -= 5;
}

void World::zoomIn() {
	left   += incLeft;
	right  -= incRight;
	top    -= incTop;
	bottom += incBottom;
}

void World::zoomOut() {
	left   -= incLeft;
	right  += incRight;
	top    += incTop;
	bottom -= incBottom;
}

void World::deleteAllObjects() {
	objsGraph.clear();
}

void World::deleteObject(ObjectGraph* _obj) {
	for (unsigned int i = 0; i < objsGraph.size(); ++i) {
		ObjectGraph* o = objsGraph.at(i);

		if (o == _obj) {
			objsGraph.erase(objsGraph.begin()+i);
			delete o;
		}
	}
}

void World::recalculateWorldDimension(int _width, int _heigth) {
	// calculando a proporcao de x e y
	double x = _width /  (double) width;
	double y = _heigth / (double) height;

	// ajustando os atributos de dimencao da tela do objeto World
	// conforme as proporcoes x e y calculas
	left   = left   * x;
	right  = right  * x;
	bottom = bottom * y;
	top    = top    * y;

	// ajustando os atributos de incremento do objeto World
	incLeft = incLeft * x;
	incRight = incRight * x;
	incBottom = incBottom * y;
	incTop = incTop * y;

	// atualiza os valores de largura e altura da tela
	width  = _width;
	height = _heigth;
}
