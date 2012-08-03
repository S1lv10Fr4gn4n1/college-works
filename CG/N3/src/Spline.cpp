#include <GL/glut.h>
#include <math.h>
#include "Spline.h"
#include "Point.h"

using namespace std;

Spline::Spline() {
	initialized = 0;
}

Spline::~Spline() {
}

void Spline::init() {
	if (pointsReference.size() == 0) {
		for (int i = 0; i < 4; ++i) {
			pointsReference.push_back(points.at(i));
		}
	}

	// guarda os pontos de referencia
	for (unsigned int i = 0; i < pointsReference.size(); ++i) {
		pointsReference.erase(pointsReference.begin()+i);
		pointsReference.insert(pointsReference.begin()+i, points.at(i));
	}

	points.clear();

	points.push_back(pointsReference.at(0));
	points.push_back(pointsReference.at(1));
	points.push_back(pointsReference.at(2));
	points.push_back(pointsReference.at(3));


	// monta os pontos da spline
	float x, y, t;
	float a, b, c, d;

	for (int i = 0; i <= 30; i++) {
		t =  (float) (i) / 30;
		a = pow((1 - t), 3);
		b = 3 * t * pow((1 - t), 2);
		c = 3 * pow(t, 2) * (1 - t);
		d = pow(t, 3);

		x = (a * points.at(0)->getX()) + (b * points.at(1)->getX()) + (c * points.at(2)->getX()) + (d * points.at(3)->getX());
		y = (a * points.at(0)->getY()) + (b * points.at(1)->getY()) + (c * points.at(2)->getY()) + (d * points.at(3)->getY());

		Point* p = new Point(x, y, 0.0f, 1.0f);
		points.push_back(p);
	}

	ObjectGraph::init();

	initialized = 1;
}

// metodo responsavel por desenhar as linhas guias do spline
void Spline::paintReferences() {
	glLineWidth(2.0f);
	glColor3b(1.0f, 1.0f, 1.0f);
	glBegin(GL_LINE_STRIP);
		int totalPoints = points.size();
		for (int i = 0; i < totalPoints; i++) {
			Point* point = points.at(i);
			glVertex2f(point->getX(), point->getY());
		}
	glEnd();
	glLineWidth(1.0f);
}

void Spline::draw() {
	if (!initialized) {
		paintReferences();
		return;
	}

	ObjectGraph::draw();

	glBegin(GL_LINE_STRIP);
		// comeca do 4, pois os 4 primeiros pontos sao os pontos
		// que dao origem a spline
		int totalPoints = points.size();
		for (int i = 4; i < totalPoints; i++) {
			Point* point = points.at(i);
			glVertex2f(point->getX(), point->getY());
		}
	glEnd();
}

// metodo sobrescrito para poder disponibilizar somente os pontos
// corretos para edicao
void Spline::nextPointHighlights() {
	indexHighlights = (indexHighlights + 1) % pointsReference.size();
}
