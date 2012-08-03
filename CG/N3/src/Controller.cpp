#include <GL/glut.h>
#include <stdarg.h>
#include <iostream>
#include <cstring>
#include <stdio.h>
#include <vector>
#include "Controller.h"
#include "World.h"
#include "Point.h"
#include "Circle.h"
#include "PolygonClose.h"
#include "PolygonOpen.h"
#include "Spline.h"

using namespace std;

// variaveis responsaveis por pegar a origem do
// inicio das transformacoes
float xSource = 0;
float ySource = 0;
bool editDelePoint = false;

// constructor, inicializacoes necessarias
Controller::Controller() {
	maxPoints   = -1;
	totalPoints = 0;
	modeEdition = false;

	world = new World();

	newGraphObject(new ObjectGraph(), -1);
}

// finalizacoes necessarias
Controller::~Controller() {
	int totalObj = world->getObjsGragh().size();

	for (int i=0; i<totalObj; i++) {
		delete world->getObjsGragh().at(i);
	}
	delete world;
	delete currentObjectGraph;
}

// metodo responsavel por imprimir a lista de opcoes possiveis no editor
void Controller::displayMenu() {
	cout << "option '1'  Circle" << endl;
	cout << "option '2'  Polygon Close" << endl;
	cout << "option '3'  Polygon Open" << endl;
	cout << "option '4'  Spline" << endl;
	cout << "option 'F1' Translate Object" << endl;
	cout << "option 'F2' Scale Object" << endl;
	cout << "option 'F3' Rotate Object" << endl;
	cout << "option 'i'  ZOOM In" << endl;
	cout << "option 'o'  ZOOM Out" << endl;
	cout << "option 'w'  PAN North" << endl;
	cout << "option 'a'  PAN West" << endl;
	cout << "option 'd'  PAN East" << endl;
	cout << "option 's'  PAN South" << endl;
	cout << "option 'r'  Back Matrix Identity" << endl;
	cout << "option 'c'  Clear all objects" << endl;
	cout << "option 'del'  Delete object select" << endl;
	cout << "option 'x'  Move point select" << endl;
	cout << "option 'tab'  Select point for move" << endl;
	cout << "option 'tab'  Select point for move" << endl;
	cout << "option 'space' finalize object" << endl;
	cout << "option 'q' Exit" << endl;
	cout << "option 'm' Display menu" << endl;
}

// metodo responsavel por retornar uma cor aleatoria para o objeto grafico
Color* Controller::getRandomColor() {
	Color* color = new Color();
	unsigned char byte;

	byte = static_cast<unsigned char>(rand() % 256);
	color->setR(byte);

	byte = static_cast<unsigned char>(rand() % 256);
	color->setG(byte);

	byte = static_cast<unsigned char>(rand() % 256);
	color->setB(byte);

	return color;
}

// metodo responsavel por fazer os controles necessarios quando a tela
// for redimencionada
void Controller::reshape(int _width, int _heigth) {
	world->recalculateWorldDimension(_width, _heigth);
	glViewport(0, 0, _width, _heigth);
}

// metodo responsavel por pegar teclas especiais do teclado F1, F2 ...
// pega o tipo de transformacao a ser usada pelo objeto grafico e o
// ponto inicial para calculos posteriores
void Controller::special(int _key, int _x, int _y) {
	if (editDelePoint)
		return;

	// pega a transformacao do objeto corrente para depois setar o tipo de trasnformacao.
	Transform* transform = currentObjectGraph->getTransform();

	// pega o ponto de origem normalizado
	Point* point = ndc(_x, _y);
	xSource = point->getX();
	ySource = point->getY();

	switch (_key) {
		case GLUT_KEY_F1:
			transform->setTypeTransform(TRANSLATE);
			break;

		case GLUT_KEY_F2:
			// XGH+POG pegar o valor inicial da matrix
			// para o objeto nao iniciar minusculo
			xSource = transform->getElement(0);
			ySource = transform->getElement(5);
			transform->setTypeTransform(SCALE);
			break;

		case GLUT_KEY_F3:
			transform->setTypeTransform(ROTATE);
			break;

		case GLUT_KEY_F4:
			transform->setTypeTransform(NONE);
			break;
	}
}

// metodo responsavel por pegar os eventos do teclado.
// utilizado para pegar as opcoes que o usuario quer fazer
// com o sistema
void Controller::keyboard(unsigned char _key, int _x, int _y) {
	switch (_key) {
	case '1':
		// seleciona para criacao o circulo
		// dando a limitacao de 2 pontos
		newGraphObject(new Circle(), 2);
		break;

	case '2':
		// seleciona para criacao o poligono fechado
		// -1 = sem limites de pontos
		newGraphObject(new PolygonClose(), -1);
		break;

	case '3':
		// seleciona para criacao o poligono aberto
		// -1 = sem limites de pontos
		newGraphObject(new PolygonOpen(), -1);
		break;

	case '4':
		// seleciona para criacao o spline
		// dado a limitacao de 4 pontos
		newGraphObject(new Spline(), 4);
		break;

	case 32:
		// finaliza a criacao do objeto grafico, no caso dos poligonos
		if (maxPoints > 0)
			return;

		finalizeConstructionObject();
		break;

	case 'R':
	case 'r':
		// volta a matriz identidade do objeto grafico selecionado/corrente
		currentObjectGraph->getTransform()->makeIdentity();
		break;

	case 'M':
	case 'm':
		// imprime o menu no prompt
		displayMenu();
		break;

	case 'Q':
	case 'q':
		// mata o programa
		exit(0);
		break;

	case 'E':
	case 'e':
		// ENTRA EM MODO EDICAO
		modeEdition = true;
		break;

	case 'G':
	case 'g':
		// deleta um ponto do objeto
		deletePoint();
		break;

	case 'B':
	case 'b':
		// mostra a bbox
		showBBox();
		break;

	// PAN
	case 'W':
	case 'w':
		// direcao norte
		world->northPAN();
		break;

	case 'A':
	case 'a':
		// direcao oeste
		world->westPAN();
		break;

	case 'S':
	case 's':
		// direcao sul
		world->southPAN();
		break;

	case 'D':
	case 'd':
		// direcao leste
		world->eastPAN();
		break;

	// ZOOM
	case 'I':
	case 'i':
		// zoom in
		world->zoomIn();
		break;

	case 'O':
	case 'o':
		// zoom out
		world->zoomOut();
		break;

	case 'X':
	case 'x':
		// movendo pontos
		setMovingPoint(_x, _y);
		break;

	case 'C':
	case 'c':
		cleanAllObjects();
		break;

	case '\t':
		nextPointInObject();
		break;

	case 27:
		// ESC, sai do modo de edicao
		modeEdition = false;
		finalizeEditionObject();
		newGraphObject(new ObjectGraph(), -1);
		break;

	case 127:
		deleteObjetct();
		break;
	}

	glutPostRedisplay();
}

// metodo responsavel por finalizar o objeto grafico corrente/em construcao
// chama o metodo init do objeto grafico e adiciona ao mundo
void Controller::finalizeConstructionObject() {
	currentObjectGraph->init();
	world->addObjsGraph(currentObjectGraph);

	// cria um novo objeto zerado
	newGraphObject(new ObjectGraph(), -1);
}

// metodo responsavel por fazer a finalizacao da edicao do objeto
void Controller::finalizeEditionObject() {
	// desativa a trasnformacao, desativa o modo edicao,
	// inicializa a bbox com os novos valores
	currentObjectGraph->getTransform()->setTypeTransform(NONE);
	currentObjectGraph->setModeEdition(false);
	currentObjectGraph->initBBox(currentObjectGraph->getTransform());
}

// metodo responsavel por criar o novo objeto grafico e limitando sua
// quantidade de pontos
void Controller::newGraphObject(ObjectGraph* _obj, int _maxPoints) {
	if (editDelePoint) {
		return;
	}

	// caso ja exista um objeto grafico corrente carregado,
	// seta tipo de transformacao para NONE e deixar o modo de
	// edicao false, assim tirando o bbox do objeto e
	// quando carregado novamente nao ter um tipo de transformacao
	// ja carregados
	if (currentObjectGraph != 0) {
		finalizeEditionObject();
	}

	// inicializacoes do novo objeto grafico
	currentObjectGraph = _obj;
	currentObjectGraph->setColor(getRandomColor());
	currentObjectGraph->getTransform()->setTypeTransform(NONE);

	// inicializacoes de variaveis de controle
	maxPoints = _maxPoints;
	totalPoints = 0;
	modeEdition = false;
}

// responsavel por receber os pontos gerados pelo mouse
// passando pela normalizacao (NDC) e passando para o objeto corrente
// para q se case tenha alguma trasnformacao a ser feita, usar os pontos
void Controller::motion(int _x, int _y) {
	Point* point = ndc(_x, _y);
	currentObjectGraph->getTransform()->transformGraphObject(point->getX() - xSource, point->getY() - ySource  , 0);

	glutPostRedisplay();
}

// controle de click do mouse
void Controller::mouse(int _button, int _state, int _x, int _y) {
	if (editDelePoint) {
		return;
	}

	if (_state == GLUT_DOWN) {
		// passa pelo NDC
		Point* point = ndc(_x, _y);

		// caso nao esteja em modo de edicao, ele ira colocar os pontos
		// gerado pelo click do mouse, no objeto grafico corrente
		if (!modeEdition) {
			currentObjectGraph->addPoint(point);
			totalPoints++;
		} else {
			// caso estiver em edicao, ele ira ver se o click é sobre
			// um objeto grafico
			selectObject(point);
		}
	}

	// verifica a quantidade maxima de pontos para
	// o circulo e spline q sao limitados a 2 e 4 pontos respectivamente
	if (maxPoints == totalPoints) {
		// finaliza o objeto
		finalizeConstructionObject();
		totalPoints = 0;
	}

	glutPostRedisplay();
}

void Controller::passive(int _x, int _y) {
	if (!editDelePoint)
		return;

	Point* point = ndc(_x, _y);
	currentObjectGraph->getTransform()->transformPoint(point);
	currentObjectGraph-> setPointHighlights(point);
	currentObjectGraph->init();
	glutPostRedisplay();
}

// metodo responsavel por desenhar todos os objetos graficos do grafo de cena
void Controller::drawAllObj() {
	// para todos os objetos desenhados abaixo:
	// - guardar a matrix interna atual do opengl
	// - setar a matrix com as transformacoes(se existirem) do objeto atual
	// - desenha o objeto
	// - volta a matrix que foi empilhada

	glPushMatrix();
		glMultMatrixf(currentObjectGraph->getTransform()->getMatrix());
		currentObjectGraph->draw();
	glPopMatrix();

	ObjectGraph* object;

	for(unsigned int i=0; i<world->getObjsGragh().size(); i++) {
		object = world->getObjsGragh().at(i);
		if (object == currentObjectGraph) {
			continue;
		}
		glPushMatrix();
			glMultMatrixf(object->getTransform()->getMatrix());
			object->draw();
		glPopMatrix();
	}
}

// metodo responsavel por fazer o NDC (Coordenadas de Dispositivos Normalizados) / normalizacao
Point* Controller::ndc(float _x, float _y) {
	Point* point;

	float x = _x * ((world->getRight() - world->getLeft()) / world->getWidth()) + world->getLeft();
	float y = _y * ((world->getBottom() - world->getTop()) / world->getHeight()) + world->getTop();

	point = new Point(x, y, 0, 1);

	return point;
}

// metodo responsavel por verificar se o ponto selecionado
// esta sobre algum objeto grafico.
void Controller::selectObject(Point* _point) {
	vector<ObjectGraph*> graphObjects = world->getObjsGragh();
	int totalObj = graphObjects.size();

	// itera sobre todos os objetos graficos existentes no mundo
	for(int i=0; i < totalObj; i++) {
		ObjectGraph* o = graphObjects.at(i);

		// verifica se esta dentro da bbox primeiro e caso esteja,
		// verifca com o metodo scan line
		if (inBBox(o, _point) && inObject(o, _point)) {
			// se for o mesmo objeto, nao faz nada
			if (currentObjectGraph == o)
				return;

			// finaliza o objeto corrente
			finalizeEditionObject();

			// atribui o novo objeto e seta o modo de edicao nele
			currentObjectGraph = o;
			currentObjectGraph->setModeEdition(true);
			return;
		}
	}
}

// metodo responsavel por ver se o ponto selecionado esta dentro da bbox
bool Controller::inBBox(ObjectGraph* _object, Point* _point) {
	// transforma os valores da bbox com a matrix de tranformacao do objeto
	_object->initBBox(_object->getTransform());

	// dai consegue verificar se o ponto pertence a bbox
	bool result = _object->getBBox()->inBBox(_point);

	// depois retorna os valores originais da bbox
	_object->initBBox(new Transform());

	return  result;
}

// metodo responsavel por ver se o ponto selecionado esta dentro do poligono
// atraves do algoritmo scanline
bool Controller::inObject(ObjectGraph* _object, Point* _point) {
	vector<Point*> points = _object->getPoints();
	int totPoints = points.size();

	int countObjectsRight = 0;
	int next = 0;
	float ti = 0;
	float xi = 0;
	Point* p1 = 0;
	Point* p2 = 0;

	for (int i=0; i<totPoints; i++) {
		p1 = points.at(i);
		p1 = _object->getTransform()->transformPoint(p1);

		next = (i + 1) % totPoints;

		p2 = points.at(next);
		p2 = _object->getTransform()->transformPoint(p2);

		// equacoes parametrica da reta
		ti = (_point->getY() - p1->getY()) / (p2->getY() - p1->getY());
		xi = p1->getX() + (p2->getX() - p1->getX()) * ti;

		if ((ti >= 0.0f && ti <= 1.0f) && xi < _point->getX()) {
			countObjectsRight++;
		}
	}

	// caso exista um numero impar de linhas cruzando a scanline a direita
	// do ponto selecionado, entao esta selecionando um objeto.
	if (countObjectsRight % 2 != 0) {
		return true;
	}

	return false;
}

// metodo responsavel por deletar o objeto selecionado
void Controller::deleteObjetct() {
	world->deleteObject(currentObjectGraph);
	currentObjectGraph = 0;
	newGraphObject(new ObjectGraph(), -1);

	glutPostRedisplay();
}

// metodo responsavel por deletar um ponto do objeto
void Controller::deletePoint() {
	if (!modeEdition)
		return;

	currentObjectGraph->deleteActualPoint();
}

// metodo responsavel pode deletar todos os objetos da mundo
void Controller::cleanAllObjects() {
	world->deleteAllObjects();
	newGraphObject(new ObjectGraph(), -1);

	glutPostRedisplay();
}

void Controller::nextPointInObject() {
	if (!modeEdition)
		return;

	currentObjectGraph->nextPointHighlights();
}

void Controller::showBBox() {
	currentObjectGraph->initBBox(new Transform());
	currentObjectGraph->showBBox();
}

void Controller::setMovingPoint(int _x, int _y) {
	if (currentObjectGraph == 0)
		return;

	Point* p = currentObjectGraph->getPointHighlights();
	p = ndc(p->getX(), p->getY());
	p = currentObjectGraph->getTransform()->transformPoint(p);
	xSource = p->getX();
	ySource = p->getY();

	editDelePoint = !editDelePoint;
}

double Controller::getOrtho2DLeft() {
	return world->getLeft();
}

double Controller::getOrtho2DRight(){
	return world->getRight();
}

double Controller::getOrtho2DBottom(){
	return world->getBottom();
}

double Controller::getOrtho2DTop() {
	return world->getTop();
}
