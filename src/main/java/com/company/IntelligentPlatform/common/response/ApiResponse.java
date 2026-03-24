package com.company.IntelligentPlatform.common.response;

/**
 * Unified API response wrapper for all REST endpoints.
 */
public class ApiResponse<T> {

	protected boolean success;

	protected String message;

	protected T data;

	private ApiResponse(boolean success, String message, T data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(true, null, data);
	}

	public static <T> ApiResponse<T> error(String message) {
		return new ApiResponse<>(false, message, null);
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public T getData() {
		return data;
	}

}
