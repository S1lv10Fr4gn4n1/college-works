#include <GL/glut.h>
#include "ObjectGraph.h"
#include "Point.h"
#include <iostream>

using namespace std;

ObjectGraph::ObjectGraph() {
	// inicializacao dos objetos que o objeto grafico ira necessitar
	transform = new Transform();
	bbox = new BBox();
	showBbox = false;
	modeEdition = false;
	indexHighlights = 0;
}

ObjectGraph::~ObjectGraph() {
}

// metodo responsavel por fazer inicializacoes necessarias antes de desenhar o objeto, exemplo o calculo
// para gerar a circunferencia.
void ObjectGraph::init() {
	// inicializa os valores da bbox
	initBBox(transform);
}

// metodo responsavel por desenhar o objeto em si
// nessa classe mais abstrata, tem o desenho da bbox para todos
// os objetos que herdarem dela.
void ObjectGraph::draw() {
	if (modeEdition) {
		glColor3b(1.0f, 1.0f, 1.0f);

		// pintando os pontos
		glPointSize(5.0f);
		glBegin(GL_POINTS);
			int totalPoints = points.size();
			for (int i=0; i<totalPoints; i++) {
				Point* p = points.at(i);
				glVertex2f(p->getX(), p->getY());
			}
		glEnd();

		// pinta o ponto atual em destaque
		Point* actualPoint = getPointHighlights();

		if (actualPoint != 0) {
			glPointSize(13.0f);
			glColor3b(0.0f, 1.0f, 0.0f);
			glBegin(GL_POINTS);
				glVertex2f(actualPoint->getX(), actualPoint->getY());
			glEnd();
		}

		if (showBbox) {
			// pintando a bbox
			glColor3b(1.0f, 1.0f, 1.0f);
			glBegin(GL_LINE_LOOP);
				glVertex2f(bbox->getXMax(), bbox->getYMax());
				glVertex2f(bbox->getXMax(), bbox->getYMin());
				glVertex2f(bbox->getXMin(), bbox->getYMin());
				glVertex2f(bbox->getXMin(), bbox->getYMax());
			glEnd();
		}
	}

	drawColor();
}

// metodo responsavel por setar a cor no objeto grafico
void ObjectGraph::drawColor() {
	glColor3b(color->getR(), color->getG(), color->getB());
}

// metodo responsavel por retornar os points pertencentes a esse objeto
std::vector<Point*> ObjectGraph::getPoints() {
	return points;
}

// metodo responsavel por adicionar point no vector de point do objeto grafico
void ObjectGraph::addPoint(Point* _point) {
	points.push_back(_point);
}

// metodo responsavel por inicializar os valores da bbox, determinado pelos
// pontos pertencente ao objeto grafico
void ObjectGraph::initBBox(Transform* _t) {
	bbox->init();

	int countPoints = points.size();

	for(int i=0; i < countPoints; i++) {
		Point* point = points.at(i);

		point = _t->transformPoint(point);
		//cout << "bbox:point: x: " << point->getX() << ", y: " << point->getY() << endl;

		//definindo o maior X
		if (point->getX() > bbox->getXMax()) {
			bbox->setXMax(point->getX());
		}

		//definindo o menor X
		if (point->getX() < bbox->getXMin()) {
			bbox->setXMin(point->getX());
		}

		//definindo o maior Y
		if (point->getY() > bbox->getYMax()) {
			bbox->setYMax(point->getY());
		}

		//definindo o menor Y
		if (point->getY() < bbox->getYMin()) {
			bbox->setYMin(point->getY());
		}
	}
}

void ObjectGraph::nextPointHighlights() {
	indexHighlights = (indexHighlights + 1) % points.size();
}

Point* ObjectGraph::getPointHighlights() {
	return points.at(indexHighlights);
}

void ObjectGraph::deleteActualPoint() {
	for (unsigned int i = 0; i < points.size(); ++i) {
		if (points.at(i) == points.at(indexHighlights)) {
			points.erase(points.begin()+i);
			return;
		}
	}
}

// sobrescreve o ponto em destaque
void ObjectGraph::setPointHighlights(Point* _point) {
	points.erase(points.begin()+indexHighlights);
	points.insert(points.begin()+indexHighlights, _point);
}

void ObjectGraph::setTransform(Transform* _t) {
	transform = _t;
}

void ObjectGraph::showBBox() {
	showBbox = !showBbox;
}

Transform* ObjectGraph::getTransform(){
	return transform;
}

void ObjectGraph::setColor(Color* _color) {
	color = _color;
}

Color* ObjectGraph::getColor() {
	return color;
}

BBox* ObjectGraph::getBBox() {
	return bbox;
}

void ObjectGraph::setModeEdition(bool _modeEdition) {
	modeEdition = _modeEdition;
}

bool ObjectGraph::getModeEdition() {
	return modeEdition;
}
