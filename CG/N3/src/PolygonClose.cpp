#include <GL/glut.h>
#include <iostream>
#include "PolygonClose.h"
#include "Point.h"

using namespace std;

PolygonClose::PolygonClose() {
}

PolygonClose::~PolygonClose() {
}

void PolygonClose::init() {
	ObjectGraph::init();
}

void PolygonClose::draw() {
	ObjectGraph::draw();

	ObjectGraph::drawColor();

	glBegin(GL_POLYGON);
		int totalPoints = points.size();
		for (int i = 0; i < totalPoints; i++) {
			Point* point = points.at(i);
			glVertex2f(point->getX(), point->getY());
		}
	glEnd();
}
