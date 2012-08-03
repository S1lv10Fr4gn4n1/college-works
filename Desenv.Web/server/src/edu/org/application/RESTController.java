package edu.org.application;

import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONStringer;
import net.sf.json.util.JSONUtils;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import edu.org.application.model.Categoria;
import edu.org.application.model.Fabricante;
import edu.org.application.model.Medida;
import edu.org.application.model.Produto;
import edu.org.application.model.SubCategoria;
import edu.org.application.model.Unidade;
import edu.org.common.IRequest;
import edu.org.common.IResponse;
import edu.org.server.SessionWeb;

public class RESTController {

	private SessionWeb	sessionWeb;

	public RESTController(SessionWeb sessionWeb) {
		this.sessionWeb = sessionWeb;
	}

	public synchronized void doAction(IRequest request, IResponse response) {
		String json = null;

		try {
			if (request.getPath().equals("/app/_REST_LISTPROD")) {
				json = this.listProds(request);
			} else if (request.getPath().equals("/app/_REST_LISTPROD1")) { //TODO metodo de teste de implementacao REST para apresentacao
				json = this.listProdsOrderby(request);
			} else if (request.getPath().equals("/app/_REST_LOADPROD")) {
				json = this.loadRegisterForEdition(request);
			} else if (request.getPath().equals("/app/_REST_COUNTPROD")) {
				json = this.countProd(request);
			} else if (request.getPath().equals("/app/_REST_SAVEPROD")) {
				json = this.saveProd(request);
			} else if (request.getPath().equals("/app/_REST_ERASEPROD")) {
				json = this.eraseProd(request);
			} else if (request.getPath().equals("/app/_REST_COMPLETE_FABRICANTE")) {
				json = this.completeFabricante(request);
			} else if (request.getPath().equals("/app/_REST_COMPLETE_UNIDADE")) {
				json = this.completeUnidade(request);
			} else if (request.getPath().equals("/app/_REST_COMPLETE_MEDIDA")) {
				json = this.completeMedida(request);
			} else if (request.getPath().equals("/app/_REST_LOADCATEGORIA")) {
				json = this.loadCategoria(request);
			} else if (request.getPath().equals("/app/_REST_LOADSUBCATEGORIA")) {
				json = this.loadSubCategoria(request);
			} else if (request.getPath().equals("/app/_REST_SAVEINSESSION")) {
				json = this.saveInSession(request);
			}

			((ResponseREST) response).setJson(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private String saveInSession(IRequest request) {
		String json = request.getParameter().get("json");
		json = URLDecoder.decode(json);

		JSON jsonAux = JSONSerializer.toJSON(json);

		if (JSONUtils.isArray(jsonAux)) {
			JSONArray jsonArray = (JSONArray) jsonAux;

			for (int j = 0; j < jsonArray.size(); j++) {
				JSONObject rec = jsonArray.getJSONObject(j);

				String key = rec.getString("key");
				String value = rec.getString("value");

				this.sessionWeb.setAttribute(key, value);
			}
		}

		if (JSONUtils.isObject(jsonAux)) {
			JSONObject rec = JSONObject.fromObject(jsonAux);
			String key = rec.getString("key");
			String value = rec.getString("value");

			this.sessionWeb.setAttribute(key, value);
		}

		return "";
	}

	@SuppressWarnings("rawtypes")
	private String countProd(IRequest request) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createSQLQuery("select count(1) from Produto");
		
		try {
			List c = query.list();
			
			if (c != null && c.size() == 1) {
				JSONStringer jsonStringer = new JSONStringer();
				jsonStringer.object().key("rows").value(c.get(0)).endObject();
				return jsonStringer.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		} 
		
		return null;
	}

	private String loadRegisterForEdition(IRequest request) {
		String idProduto = (String) sessionWeb.getAttributes().remove("codigoProduto");
		
		if (idProduto == null) {
			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.object().key("message").value("").endObject();
			return jsonStringer.toString();
		}
		
		Produto produto = this.findProduto(idProduto);
		
		if (produto != null) {
			String nameTemp = "temp/" + this.sessionWeb.getSessionCookie().getValue() + ".jpg";
			this.saveTempImage(produto.getImgBarras(), nameTemp);

			JSONObject jsonObject = JSONObject.fromObject(produto);
			jsonObject.accumulate("imgSrc", nameTemp);

			produto.setImgBarras(null);
			
			return jsonObject.toString();
		} else {
			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.object().key("message").value("").endObject();
			return jsonStringer.toString();
		}
	}

	private void saveTempImage(byte[] imgBarras, String nameTemp) {
        try {
            FileOutputStream fos = new FileOutputStream("root/app/"+nameTemp);  
            fos.write(imgBarras);  
            fos.flush();  
            fos.close();               
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@SuppressWarnings("unchecked")
	private String loadCategoria(IRequest request) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("select c from " + Categoria.class.getName() + " c");

		try {
			List<Categoria> categorias = query.list();
			JSONArray jsonArray = JSONArray.fromObject(categorias);
			
			return jsonArray.toString();
		} catch (Exception e) {
			e.printStackTrace();

			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.object().key("message").value(e.getMessage()).endObject();
			return jsonStringer.toString();
		} finally {
			session.close();
		}

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private String loadSubCategoria(IRequest request) {
		String json = request.getParameter().get("json");
		json = URLDecoder.decode(json);
		
		JSON jsonAux = JSONSerializer.toJSON(json);
		JSONObject rec = JSONObject.fromObject(jsonAux);
		String idCat = rec.getString("idCategoria");

		if (idCat == null || idCat.equals("")) {
			return null;
		}

		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("select sc from " + SubCategoria.class.getName() + " sc where sc.categoria.id = " + idCat);
		
		try {
			List<SubCategoria> subCategorias = query.list();
			JSONArray jsonArray = JSONArray.fromObject(subCategorias);
			
			return jsonArray.toString();
		} catch (Exception e) {
			e.printStackTrace();
	
			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.object().key("message").value(e.getMessage()).endObject();
			return jsonStringer.toString();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	private String completeMedida(IRequest request) {
		String medidaLike = request.getParameter().get("medida");

		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("select m.descricao from " + Medida.class.getName() + " m where m.descricao like '" + medidaLike + "%'");
		
		try {
			List<Medida> medidas = query.list();
			JSONArray jsonArray = JSONArray.fromObject(medidas);
	
			return jsonArray.toString();
		} catch (Exception e) {
			e.printStackTrace();
	
			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.object().key("message").value(e.getMessage()).endObject();
			return jsonStringer.toString();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	private String completeUnidade(IRequest request) {
		String unidadeLike = request.getParameter().get("unidade");

		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("select u.descricao from " + Unidade.class.getName() + " u where u.descricao like '" + unidadeLike + "%'");
		
		try {
			List<Unidade> unidades = query.list();
			JSONArray jsonArray = JSONArray.fromObject(unidades);
	
			return jsonArray.toString();
		} catch (Exception e) {
			e.printStackTrace();

			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.object().key("message").value(e.getMessage()).endObject();
			return jsonStringer.toString();
		} finally {
			session.close();
		}

	}

	@SuppressWarnings("unchecked")
	private String completeFabricante(IRequest request) {
		String fabricanteLike = request.getParameter().get("fabricante");

		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("select f.descricao from " + Fabricante.class.getName() + " f where f.descricao like '" + fabricanteLike + "%'");
		
		try {
			List<Produto> produtos = query.list();
			JSONArray jsonArray = JSONArray.fromObject(produtos);
	
			return jsonArray.toString();
		} catch (Exception e) {
			e.printStackTrace();

			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.object().key("message").value(e.getMessage()).endObject();
			return jsonStringer.toString();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("deprecation")
	private String eraseProd(IRequest request) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		try {
			String json = request.getParameter().get("json");
			json = URLDecoder.decode(json);

			JSON jsonAux = JSONSerializer.toJSON(json);
			JSONObject rec = JSONObject.fromObject(jsonAux);
			String idProd = rec.getString("codigoProduto");
			
			Produto produto = this.findProduto(idProd);

			if (produto == null) {
				JSONStringer jsonStringer = new JSONStringer();
				jsonStringer.object().key("message").value("Problemas: produto não foi encontrado").endObject();
				return jsonStringer.toString();
			}

			session.delete(produto);
			session.flush();

			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();

			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.object().key("message").value(e.getMessage()).endObject();
			return jsonStringer.toString();
		} finally {
			session.close();
		} 

		JSONStringer jsonStringer = new JSONStringer();
		jsonStringer.object().key("message").value("Produto removido com sucesso!").endObject();
		return jsonStringer.toString();
	}

	private Produto findProduto(String idProd) {
		if (idProd == null || idProd.trim().isEmpty()) {
			return null;
		}

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Long id = Long.valueOf(idProd);
	
			if (idProd != null && !idProd.equals("")) {
				return (Produto) session.get(Produto.class, id);
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		} finally {
			session.close();
		}

		return null;
	}

	private String saveProd(IRequest request) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		Map<String, String> parameter = request.getParameter();

		try {
			String idProd = parameter.get("idProduto");
			Produto produto = this.findProduto(idProd);

			if (produto == null) {
				produto = new Produto();
			}
			
			String nomeProduto = parameter.get("nomeProduto");
			
			if (nomeProduto == null || nomeProduto.trim().isEmpty()) {
				JSONStringer jsonStringer = new JSONStringer();
				jsonStringer.object().key("message").value("Nome do produto é obrigatorio!").endObject();
				return jsonStringer.toString();
			}
			
			produto.setCodBarras(parameter.get("codigoBarras"));
			produto.setDescricao(nomeProduto);

			String paramCategoria = parameter.get("categoria");
			if (paramCategoria == null || paramCategoria.trim().isEmpty()) {
				JSONStringer jsonStringer = new JSONStringer();
				jsonStringer.object().key("message").value("Categoria é obrigatorio!").endObject();
				return jsonStringer.toString();
			}
			Long idCategoria = Long.valueOf(paramCategoria);
			Categoria categoria = (Categoria) session.get(Categoria.class, idCategoria);
			produto.setCategoria(categoria);


			String paramSubCategoria = parameter.get("subCategoria");
			if (paramSubCategoria == null || paramSubCategoria.trim().isEmpty()) {
				JSONStringer jsonStringer = new JSONStringer();
				jsonStringer.object().key("message").value("Sub-Categoria é obrigatorio!").endObject();
				return jsonStringer.toString();
			}
			Long idSubCategoria = Long.valueOf(paramSubCategoria);
			SubCategoria subCategoria = (SubCategoria) session.get(SubCategoria.class, idSubCategoria);
			produto.setSubCategoria(subCategoria);

			if (request.getFiles().size() > 0) {
				byte[] barras = request.getFiles().get(0).getData();
				produto.setImgBarras(barras);
			}

			Fabricante fabricante = this.getFabricanteFromDesc(session, parameter.get("fabricante"));
			produto.setFabricante(fabricante);

			Unidade unidade = this.getUnidadeFromDesc(session, parameter.get("unidade"));
			produto.setUnidade(unidade);

			Medida medida = this.getMedidaFromDesc(session, parameter.get("medida"));
			produto.setMedida(medida);

			if (idProd == null) {
				session.save(produto);
			} else {
				session.merge(produto);
			}
			
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
			
			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.object().key("message").value(e.getMessage()).endObject();
			return jsonStringer.toString();
		} finally {
			session.close();
		}

		JSONStringer jsonStringer = new JSONStringer();
		jsonStringer.object().key("message").value("cadOK").endObject();
		return jsonStringer.toString();
	}

	@SuppressWarnings("unchecked")
	private Fabricante getFabricanteFromDesc(Session session, String param) throws Exception {
		Query query = session.createQuery("select f from " + Fabricante.class.getName() + " f where f.descricao = :pFabricante");
		query.setParameter("pFabricante", param);

		List<Fabricante> fabricantes = query.list();

		if (fabricantes.size() == 0) {
			throw new Exception("Fabricante nao encontrado!");
		}

		if (fabricantes.size() > 1) {
			throw new Exception("Existe mais de um fabricante com o mesmo nome!");
		}

		return fabricantes.get(0);
	}

	@SuppressWarnings("unchecked")
	private Medida getMedidaFromDesc(Session session, String param) throws Exception {
		Query query = session.createQuery("select m from " + Medida.class.getName() + " m where m.descricao = :pMedida");
		query.setParameter("pMedida", param);

		List<Medida> medidas = query.list();

		if (medidas.size() == 0) {
			throw new Exception("Medida não encontrado!");
		}

		if (medidas.size() > 1) {
			throw new Exception("Existe mais de uma medida com o mesmo nome!");
		}

		return medidas.get(0);
	}

	@SuppressWarnings("unchecked")
	private Unidade getUnidadeFromDesc(Session session, String param) throws Exception {
		Query query = session.createQuery("select u from " + Unidade.class.getName() + " u where u.descricao = :pUnidade");
		query.setParameter("pUnidade", param);

		List<Unidade> unidades = query.list();

		if (unidades.size() == 0) {
			throw new Exception("Unidade não encontrado!");
		}

		if (unidades.size() > 1) {
			throw new Exception("Existe mais de uma unidade com o mesmo nome!");
		}

		return unidades.get(0);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private String listProds(IRequest request) throws Exception {
		String json = request.getParameter().get("json");
		json = URLDecoder.decode(json);

		JSON jsonAux = JSONSerializer.toJSON(json);
		JSONObject rec = JSONObject.fromObject(jsonAux);
		String init = rec.getString("init");

		int fistResult = (init != null && !init.trim().isEmpty() ? Integer.parseInt(init) : 0);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query query = session.createQuery("select p from " + Produto.class.getName() + " p");
			query.setFirstResult(fistResult*5);
			query.setMaxResults(5);
	
			List<Produto> produtos = query.list();
			
			this.cleanBarras(produtos);
			
			JSONArray jsonArray = JSONArray.fromObject(produtos);
	
			return jsonArray.toString();
		} catch (Exception e) {
			e.printStackTrace();

			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.object().key("message").value(e.getMessage()).endObject();
			return jsonStringer.toString();
		} finally {
			session.close();
		}
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	private String listProdsOrderby(IRequest request) {
		String json = request.getParameter().get("json");
		json = URLDecoder.decode(json);

		JSON jsonAux = JSONSerializer.toJSON(json);
		JSONObject rec = JSONObject.fromObject(jsonAux);
		String init = rec.getString("init");

		int fistResult = (init != null && !init.trim().isEmpty() ? Integer.parseInt(init) : 0);

		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query query = session.createQuery("select p from " + Produto.class.getName() + " p order by p.descricao");
			query.setFirstResult(fistResult*5);
			query.setMaxResults(5);
	
			List<Produto> produtos = query.list();
			this.cleanBarras(produtos);
			
			JSONArray jsonArray = JSONArray.fromObject(produtos);
	
			return jsonArray.toString();
		} catch (Exception e) {
			e.printStackTrace();

			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.object().key("message").value(e.getMessage()).endObject();
			return jsonStringer.toString();
		} finally {
			session.close();
		}
	}

	private void cleanBarras(List<Produto> produtos) {
		for (Produto produto : produtos) {
			produto.setImgBarras(null);
		}
	}

}
