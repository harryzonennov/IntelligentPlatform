package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.RegisteredProductException;
import com.company.IntelligentPlatform.common.service.ServiceEntityRuntimeException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.SimpleSEMessageResponse;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends
		ResponseEntityExceptionHandler {

	private static final Logger log =
			LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

	// -------------------------------------------------------------------------
	// Legacy session-tier handlers (unchanged)
	// These return plain JSON strings consumed by the legacy UI layer.
	// -------------------------------------------------------------------------

	@ExceptionHandler({ RegisteredProductException.class,
			MaterialException.class })
	public @ResponseBody String handleMaterialException(RuntimeException ex,
			WebRequest request) {
		List<SimpleSEMessageResponse> errorMessageList = new ArrayList<SimpleSEMessageResponse>();
		SimpleSEMessageResponse messageResponse = new SimpleSEMessageResponse();
		ServiceEntityRuntimeException serviceEntityRuntimeException = (ServiceEntityRuntimeException) ex;
		messageResponse.setMessageContent(serviceEntityRuntimeException
				.getErrorMessage());
		messageResponse
				.setMessageLevel(SimpleSEMessageResponse.MESSAGELEVEL_ERROR);
		messageResponse.setRefException(serviceEntityRuntimeException);
		messageResponse.setErrorCode(serviceEntityRuntimeException
				.getErrorCode());
		errorMessageList.add(messageResponse);
		return ServiceJSONParser.generateErrorJSONMessageArray(errorMessageList);
	}

	@ExceptionHandler({UnexpectedRollbackException.class, TransactionException.class})
	public ResponseEntity<Map<String, Object>> handleTransactionException(TransactionException ex,
			WebRequest request) {
		log.error("Transaction error: {} — {}", request.getDescription(false), ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of(
						"error", "Internal Server Error",
						"message", "A transaction error occurred"
				));
	}

	// -------------------------------------------------------------------------
	// JWT REST tier handlers — return proper HTTP status codes + JSON body.
	// These apply to all /api/v1/** endpoints.
	// -------------------------------------------------------------------------

	/**
	 * 403 Forbidden — the authenticated user lacks permission for the operation.
	 *
	 * <p>Thrown by {@link com.company.IntelligentPlatform.common.config.JwtRestContext#checkAuthorization}
	 * when the user's {@code authorizationActionCodeMap} does not contain the
	 * required action code for the requested resource.</p>
	 */
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<Map<String, Object>> handleAuthorizationException(
			AuthorizationException ex, WebRequest request) {
		log.warn("Authorization denied: {} — {}", request.getDescription(false), ex.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(Map.of(
						"error", "Forbidden",
						"message", ex.getMessage() != null ? ex.getMessage() : "Access denied"
				));
	}

	/**
	 * 401 Unauthorized — no valid authenticated user for this request.
	 *
	 * <p>Thrown by {@link com.company.IntelligentPlatform.common.config.JwtRestContext#getLogonInfo}
	 * when the JWT principal is missing or the user UUID no longer exists in the DB.</p>
	 */
	@ExceptionHandler(LogonInfoException.class)
	public ResponseEntity<Map<String, Object>> handleLogonInfoException(
			LogonInfoException ex, WebRequest request) {
		log.warn("Logon context missing: {} — {}", request.getDescription(false), ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(Map.of(
						"error", "Unauthorized",
						"message", "Authentication required or session expired"
				));
	}

	/**
	 * 500 Internal Server Error — a DB or configuration lookup failed during
	 * request processing.
	 *
	 * <p>Thrown by service and repository methods throughout the codebase when
	 * a required configuration or database record is missing.  The root cause is
	 * logged at ERROR level; only a generic message is returned to the caller to
	 * avoid leaking internal details.</p>
	 */
	@ExceptionHandler(ServiceEntityConfigureException.class)
	public ResponseEntity<Map<String, Object>> handleServiceEntityConfigureException(
			ServiceEntityConfigureException ex, WebRequest request) {
		log.error("Service configuration error: {} — {}", request.getDescription(false), ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of(
						"error", "Internal Server Error",
						"message", "A server-side configuration error occurred"
				));
	}

	/**
	 * Catch-all — any unhandled exception returns 500 with a JSON body instead
	 * of Spring's default HTML error page.  The full stack trace is logged at
	 * ERROR level; only a generic message is returned to the caller so that
	 * internal details are never leaked to API consumers.
	 *
	 * <p>This handler has the lowest priority — all more-specific handlers above
	 * take precedence for their exception types.</p>
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleUnexpectedException(
			Exception ex, WebRequest request) {
		log.error("Unexpected error: {} — {}", request.getDescription(false), ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of(
						"error", "Internal Server Error",
						"message", "An unexpected error occurred"
				));
	}

}

