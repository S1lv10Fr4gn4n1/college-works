#include <iostream>
#include <cmath>
#include "Transform.h"
#include "Point.h"
#include "TypeTransform.h"

using namespace std;

// constructor
Transform::Transform() {
	// inicializa a matrix do objeto como identidade
	makeIdentity();
}

Transform::~Transform() {
}

// metodo responsavel por transformar um ponto original
// para um ponto resultando da transformacao
Point* Transform::transformPoint(Point* _point) {
	Point* result = new Point(matrix[0] * _point->getX() + matrix[4] * _point->getY() + matrix[8]  * _point->getZ() + matrix[12] * _point->getW(),
							  matrix[1] * _point->getX() + matrix[5] * _point->getY() + matrix[9]  * _point->getZ() + matrix[13] * _point->getW(),
							  matrix[2] * _point->getX() + matrix[6] * _point->getY() + matrix[10] * _point->getZ() + matrix[14] * _point->getW(),
							  matrix[3] * _point->getX() + matrix[7] * _point->getY() + matrix[11] * _point->getZ() + matrix[15] * _point->getW());

	return result;
}

// metodo responsavel por multiplicacao de matrizes
Transform* Transform::transformMatrix(Transform* t) {
	Transform* result = new Transform();

	for (int i = 0; i < 16; i++) {
		result->setElement(i, matrix[i % 4] * t->getElement(i / 4 * 4) +
							  matrix[(i % 4) + 4] * t->getElement(i / 4 * 4 + 1) +
						      matrix[(i % 4) + 8] * t->getElement(i / 4 * 4 + 2) +
						      matrix[(i % 4) + 12] * t->getElement(i / 4 * 4 + 3));
	}

	return result;
}

// metodo responsavel por gerar a matrix identidade
void Transform::makeIdentity() {
	for (int i = 0; i < 16; i++) {
		matrix[i] = 0.0;
	}

	matrix[0] = matrix[5] = matrix[10] = matrix[15] = 1.0;
}

float* Transform::getMatrix() {
	return matrix;
}

float Transform::getElement(int _i) {
	return matrix[_i];
}

void Transform::setElement(int _i, float _value) {
	matrix[_i] = _value;
}

// metodo responsavel por ajustar a matrix para a transformacao de translacao
void Transform::translate(float _x, float _y, float _z) {
	// translacao (tx, ty, tz) é matrix identidade
	matrix[12] = _x;
	matrix[13] = _y;
	matrix[14] = 0.0f;
	matrix[15] = 1.0f;
}

// metodo responsavel por ajustar a matrix para a transformacao de scalar
void Transform::scale(float _x, float _y, float _z) {
	// scale (sx, sy, sz) é matrix identidade
	matrix[0] =  (_x / 100);
	matrix[5] =  (_y / 100);
	matrix[10] = 1.0f;
	matrix[15] = 1.0f;
}

// metodo responsavel por ajustar a matrix para a transformacao de rotacao
void Transform::rotate(float _x, float _y, float _z) {
	// rotaciona em Z
	float teta = (M_PI * _x * 10) / 180.0; // teta eh a letra grega q referencia o grau e nao os seios de uma mulher :D
	matrix[0] =  cos(teta);
	matrix[1] =  sin(teta);
	matrix[4] = -sin(teta);
	matrix[5] =  cos(teta);
}

// metodo responsavel por direcionar os ajustes para cada metodo
void Transform::transformGraphObject(float _x, float _y, float _z) {
	switch (typeTransform) {
		case TRANSLATE:
			translate(_x, _y, _z);
			break;

		case SCALE:
			scale(_x, _y, _z);
			break;

		case ROTATE:
			rotate(_x, _y, _z);
			break;

		case NONE:
			break;
	}
}

void Transform::setTypeTransform(TypeTransform _type) {
	typeTransform = _type;
}

// printa a matrix atual
void Transform::printMatrix(string _desc) {
	cout << "___" << _desc << " ___" << endl;
	cout << "|" << matrix[0] << " | " << matrix[1] << " | " << matrix[2] << " | " << matrix[3] << endl;
	cout << "|" << matrix[4] << " | " << matrix[5] << " | " << matrix[6] << " | " << matrix[7] << endl;
	cout << "|" << matrix[8] << " | " << matrix[9] << " | " << matrix[10] << " | " << matrix[11] << endl;
	cout << "|" << matrix[12] << " | " << matrix[13] << " | " << matrix[14] << " | " << matrix[15] << endl;
	cout << "_______________" << endl;
}
