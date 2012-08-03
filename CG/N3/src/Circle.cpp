#include <GL/glut.h>
#include <math.h>
#include <iostream>
#include "Circle.h"
#include "Point.h"

// constructor
Circle::Circle() {
	// inicializa com -1 para indicar q ainda nao foi criado o circulo ainda.
	radius = -1;
	indexHighlights = 1;
}

Circle::~Circle() {
}

// metodo responsavel por inicializacao antes de desenhar o objeto
void Circle::init() {
	// pega o primeiro ponto, que idicara a origem do circulo
	pointInit = points.at(0);
	Point* p2 = points.at(1);

	// limpa lista de pontos e volta a colocar os pontos init e p2
	points.clear();
	points.push_back(pointInit);
	points.push_back(p2);

	float x = pointInit->getX() - p2->getX();
	float y = pointInit->getY() - p2->getY();

	// faz o calculo para pegar o raio do circulo
	// conforme os pontos selecionados
	//d²=(x0-x)²+(y0-y)²
	float d = x*x + y*y;
	radius = pow(d, 0.5);

	float x1;
	float y1;

	// gera pontos para criar o circulo, esses pontos sao guardados
	// para posteriormente pode ser usados no algoritmo de scanline
	for (int i=0; i<36; i++) {
		x1 = (radius * cos(M_PI * i*10 / 180.0f));
		y1 = (radius * sin(M_PI * i*10 / 180.0f));

		Point* p = new Point(x1 + pointInit->getX(), y1 + pointInit->getY(), 0.0f, 1.0f);
		points.push_back(p);
	}

	// chama o init do pai (criacao da bbox)
	ObjectGraph::init();
}

void Circle::draw() {
	// caso ainda nao tenha gerado o raio, nao desenha o objeto
	if (radius == -1) {
		return;
	}

	// chama o draw do pai para poder desenhar o bbox
	// em torno do objeto grafico quando no estado de edicao
	// e passar a cor
	ObjectGraph::draw();

	glBegin(GL_LINE_LOOP);
		// comeca apartir do 2 o ponto de origem e o secundario responsavel
		// de gerar o raio, se mante-los, o circulo fica defeituoso
		for (unsigned int i = 2; i < points.size(); i++) {
			Point* point = points.at(i);
			glVertex2f(point->getX(), point->getY());
		}
	glEnd();
}

void Circle::nextPointHighlights() {
	// somente o ponto q da o raio
	indexHighlights = 1;
}

