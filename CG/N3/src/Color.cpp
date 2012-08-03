/*
 * Color.cpp
 *
 *  Created on: 02/05/2011
 *      Author: silvio
 */
#include "Color.h"
#include <iostream>

Color::Color(){
}

Color::Color(unsigned char _r, unsigned char _g, unsigned char _b) {
	r = _r;
	g = _g;
	b = _b;
}

Color::~Color(){
}

unsigned char Color::getR() {
	return r;
}

void Color::setR(unsigned char _r) {
	r = _r;
}

unsigned char Color::getG() {
	return g;
}

void Color::setG(unsigned char _g) {
	g = _g;
}

unsigned char Color::getB() {
	return b;
}

void Color::setB(unsigned char _b) {
	b = _b;
}
