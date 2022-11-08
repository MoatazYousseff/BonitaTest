package com.company.rest.api;

import java.sql.Date
import java.sql.SQLException
import java.text.DateFormat
import java.text.Format
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.Year

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.bonitasoft.web.extension.ResourceProvider
import org.bonitasoft.web.extension.rest.RestAPIContext
import org.bonitasoft.web.extension.rest.RestApiController
import org.bonitasoft.web.extension.rest.RestApiResponse
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.company.model.MetaData
import com.company.model.UserData

//import ch.qos.logback.core.joran.conditional.ElseAction
import groovy.json.JsonBuilder
import groovy.sql.Sql

class Index implements RestApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(Index.class)

	@Override
	RestApiResponse doHandle(HttpServletRequest request, RestApiResponseBuilder responseBuilder, RestAPIContext context) {
		// To retrieve query parameters use the request.getParameter(..) method.
		// Be careful, parameter values are always returned as String values

		// Retrieve national_id parameter
		def national_id = request.getParameter "national_id"
		def reg_no= request.getParameter "reg_no"
		if (national_id == null && reg_no==null) {
			return null// buildResponse(responseBuilder, HttpServletResponse.SC_BAD_REQUEST,"""{"error" : "the parameter national_id is missing"}""")
		}
		// Retrieve reg_no parameter

		/*if (reg_no == null) {
		 return null// buildResponse(responseBuilder, HttpServletResponse.SC_BAD_REQUEST,"""{"error" : "the parameter national_id is missing"}""")
		 }*/

		def db = Sql.newInstance("jdbc:mysql://localhost:3306/bank", "root", "2381999", "com.mysql.cj.jdbc.Driver")

		UserData u = new UserData();

		List<MetaData>docs=new ArrayList<MetaData>()
		//http://localhost:8080/bonita/API/extension/newapi?reg_no=M88888888
		//def results = new HashMap()
		//List<MetaData>meta=new List()
		try {
			if(national_id!=null) {
				def qu=db.rows("select * from users where national_id='${national_id}'")
				def qu2=db.rows("select * from doc_metadata where u_id='${qu.get(0).user_id}'")

				//LOGGER.info(qu.toString())
				for(int i=0;i< qu2.size();i++) {
					MetaData md=new MetaData();
					md.setName(qu2.get(i).name)
					//md.setExpiry_date(qu2.get(i).expiry_date)
					md.setNotes(qu2.get(i).notes)
					md.setType(qu2.get(i).type)
					//md.setUpload_date(qu2.get(i).upload_date)
					md.setUrl(qu2.get(i).url)
					docs.add(md)
				}
				////////////////////


				String format = 'yyyy-MM-dd';
				def sdf = new SimpleDateFormat("yyyy-MM-dd")


				//LOGGER.info(qu2.toString())
				//LOGGER.info(qu.toString())
				u.setFirst_name(qu.get(0).first_name)
				u.setLast_name(qu.get(0).last_name)
				u.setEmail(qu.get(0).email)
				u.setNational_id(qu.get(0).national_id)
				u.setPhone(qu.get(0).phone)
		
				Date bd = qu.get(0).birth_date;
				
				//LocalDate ld = new LocalDate(bd.getYear(), bd.getMonth(), bd.getDay())
				/*
				ld.year=y
				ld.month=m
				ld.day=d
				*/
				u.setBirth_date(bd.toLocalDate())
				//u.setBirth_date(java.time.LocalDate.parse(qu.get(0).birth_date.toString()))
				//u.setBirth_date(LocalDate.parse(sdf.format(qu.get(0).birth_date)))
				u.setMonthly_income(qu.get(0).monthly_income)
				u.setAddress(qu.get(0).address)
				u.setEducation(qu.get(0).education)
				u.setCurrent_job(qu.get(0).current_job)
				u.setGender(qu.get(0).gender)
				u.setNotes(qu.get(0).notes)
				u.setRegisteration_no(qu.get(0).registration_no)
				u.setRejection_reasons(qu.get(0).rejection_reasons)
				u.setDecision(qu.get(0).decision)
				u.setId_type(qu.get(0).id_type)
				u.setDocs(docs)
			}
			else if(reg_no!=null) {
				def qu=db.rows("select * from users where registration_no='${reg_no}'")
				def qu2=db.rows("select * from doc_metadata where u_id='${qu.get(0).user_id}'")

				//LOGGER.info(qu.toString())
				for(int i=0;i< qu2.size();i++) {
					MetaData md=new MetaData();
					md.setName(qu2.get(i).name)
					//md.setExpiry_date(qu2.get(i).expiry_date)
					md.setNotes(qu2.get(i).notes)
					md.setType(qu2.get(i).type)
					//md.setUpload_date(qu2.get(i).upload_date)
					md.setUrl(qu2.get(i).url)
					docs.add(md)
				}
				////////////////////


				String format = 'yyyy-MM-dd';
				def sdf = new SimpleDateFormat("yyyy-MM-dd")


				//LOGGER.info(qu2.toString())
				//LOGGER.info(qu.toString())
				u.setFirst_name(qu.get(0).first_name)
				u.setLast_name(qu.get(0).last_name)
				u.setEmail(qu.get(0).email)
				u.setNational_id(qu.get(0).national_id)
				u.setPhone(qu.get(0).phone)
				//locaate date = qu.get(0).birth_date


				//u.setBirth_date(LocalDate.parse(sdf.format(qu.get(0).birth_date)))
				u.setMonthly_income(qu.get(0).monthly_income)
				u.setAddress(qu.get(0).address)
				u.setEducation(qu.get(0).education)
				u.setCurrent_job(qu.get(0).current_job)
				u.setGender(qu.get(0).gender)
				u.setNotes(qu.get(0).notes)
				u.setRegisteration_no(qu.get(0).registration_no)
				u.setRejection_reasons(qu.get(0).rejection_reasons)
				u.setDecision(qu.get(0).decision)
				u.setId_type(qu.get(0).id_type)
				u.setDocs(docs)
			}
		}
		catch (Exception e) {
			e.printStackTrace()
		}
		/* 
		 * Execute business logic here
		 * 
		 * 
		 * Your code goes here
		 * 
		 * 
		 */
		// Prepare the result
		//def result = [ "national_id" : national_id , "myParameterKey" : paramValue, "currentDate" : LocalDate.now().toString() ]

		// Send the result as a JSON representation
		// You may use buildPagedResponse if your result is multiple
		return buildResponse(responseBuilder, HttpServletResponse.SC_OK, new JsonBuilder(u).toPrettyString())
	}

	/**
	 * Build an HTTP response.
	 *
	 * @param  responseBuilder the Rest API response builder
	 * @param  httpStatus the status of the response
	 * @param  body the response body
	 * @return a RestAPIResponse
	 */
	RestApiResponse buildResponse(RestApiResponseBuilder responseBuilder, int httpStatus, Serializable body) {
		return responseBuilder.with {
			withResponseStatus(httpStatus)
			withResponse(body)
			build()
		}
	}

	/**
	 * Returns a paged result like Bonita BPM REST APIs.
	 * Build a response with a content-range.
	 *
	 * @param  responseBuilder the Rest API response builder
	 * @param  body the response body
	 * @param  p the page index
	 * @param  c the number of result per page
	 * @param  total the total number of results
	 * @return a RestAPIResponse
	 */
	RestApiResponse buildPagedResponse(RestApiResponseBuilder responseBuilder, Serializable body, int p, int c, long total) {
		return responseBuilder.with {
			withContentRange(p,c,total)
			withResponse(body)
			build()
		}
	}

	/**
	 * Load a property file into a java.util.Properties
	 */
	Properties loadProperties(String fileName, ResourceProvider resourceProvider) {
		Properties props = new Properties()
		resourceProvider.getResourceAsStream(fileName).withStream { InputStream s ->
			props.load s
		}
		props
	}
}
