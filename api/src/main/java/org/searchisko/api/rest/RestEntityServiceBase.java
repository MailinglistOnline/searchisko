/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.searchisko.api.rest;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.searchisko.api.rest.exception.RequiredFieldException;
import org.searchisko.api.util.SearchUtils;
import org.searchisko.persistence.service.EntityService;

/**
 * Base class for REST API for entity manipulation, contains basic CRUD operations.
 * 
 * @author Libor Krzyzanek
 * @author Vlastimil Elias (velias at redhat dot com)
 * 
 */
public class RestEntityServiceBase extends RestServiceBase {

	protected EntityService entityService;

	/**
	 * Set entity service used by this REST service.
	 * 
	 * @param entityService
	 */
	protected void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getAll(@QueryParam("from") Integer from, @QueryParam("size") Integer size) {
		return entityService.getAll(from, size, null);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object get(@PathParam("id") String id) {

		if ((id = SearchUtils.trimToNull(id)) == null) {
			throw new RequiredFieldException("id");
		}

		Map<String, Object> ret = entityService.get(id);
		if (ret == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return ret;
	}

	protected Object getFiltered(String id, String[] fieldsToRemove) {
		Map<String, Object> ret = entityService.get(id);
		if (ret == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			return ESDataOnlyResponse.removeFields(ret, fieldsToRemove);
		}
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object create(Map<String, Object> data) {
		String id = entityService.create(data);
		return createResponseWithId(id);
	}

	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object create(@PathParam("id") String id, Map<String, Object> data) {

		if ((id = SearchUtils.trimToNull(id)) == null) {
			throw new RequiredFieldException("id");
		}

		entityService.create(id, data);
		return createResponseWithId(id);
	}

	@DELETE
	@Path("/{id}")
	public Object delete(@PathParam("id") String id) {
		if ((id = SearchUtils.trimToNull(id)) == null) {
			throw new RequiredFieldException("id");
		}

		entityService.delete(id);
		return Response.ok().build();
	}

}
