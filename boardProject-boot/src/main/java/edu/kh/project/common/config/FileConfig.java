package edu.kh.project.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.MultipartConfigElement;

@Configuration
@PropertySource("classpath:/config.properties")
public class FileConfig implements WebMvcConfigurer{

	// WebMvcConfigurer : Spring MVC 프레임워크에서 제공하는 인터페이스 중 하나로
	// 스프링 고성을 커스터마이징하고 확장하기 위한 메서드를 제공한다
	// 주로 웹 애플리케이션의 설정을 조정하거나 추가하는데 사용된다
	
	// 파일 업로드 임계값
	@Value("${spring.servlet.multipart.file-size-threshold}")
	private long fileSizeTreshold; // 52428800
	
	// 요청당 최대 크기
	@Value("${spring.servlet.multipart.max-request-size}")
	private long maxRequestSize; // 52428800
	
	// 개별 파일당 최대 크기
	@Value("${spring.servlet.multipart.max-file-size}")
	private long maxFileSize; // 10485760
	
	// 임계값 초과 시 파일의 임시 저장경로
	@Value("${spring.servlet.multipart.location}")
	private String location; // C:/uploadFiles/temp/
	
	//----------------------------------------------------
	
	// 프로필 이미지 관련 경로
	@Value("${my.profile.resource-handler}")
	private String profileResourceHandler;
	
	@Value("${my.profile.resource-location}")
	private String profileResourceLocation;
	
	//----------------------------------------------------
	
	// 게시판 이미지 관련 경로
	@Value("${my.board.resource-handler}")
	private String boardResourceHandler;
	
	@Value("${my.board.resource-location}")
	private String boardResourceLoaction;
	
	
	// 요청 주소에 따라
	// 서버 컴퓨터의 어떤 경로에 접근할지 설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		// ResourceHandlerRegistry : 
		// Spring MVC의 정적 리소스(JS, CSS , 이미지 파일 등)의 요청 처리를 위한 클래스
		
		// URL 요청 패턴을 서버의 실제 파일 경로와 연결하여
		// 클라이언트가 특정 경로로 정적파일에 접근할 수 있도록 설정
		registry.addResourceHandler("/myPage/file/**") // 클라이언트 요청 주소 패턴
		.addResourceLocations("file:///C:/uploadFiles/test/"); // 요청을 연결해서 처리해줄 서버의 실제 폴더 경로
		
		// -> 클라이언트가 /myPage/file/** 패턴으로 이미지를 요청할 때
		// 서버 폴더 경로 중 C:/uploadFiles/test/로 연결하겠다
		
		registry.addResourceHandler(profileResourceHandler)
		.addResourceLocations(profileResourceLocation);
		
		
		registry.addResourceHandler(boardResourceHandler)
		.addResourceLocations(boardResourceLoaction);
	}
	
	// MultipartResolver 설정
	
	@Bean
	public MultipartConfigElement configElement() {
		// MultipartConfigElement : 
		// 파일 업로드를 처리하는데 사용되는 MultipartConfigElement를 구성하고 반환
		// 파일 업로드를 위한 구성 옵션을 설정하는데 사용되는 객체
		// 업로드 파일의 최대 크기, 메모리에서의 임시 저장경로 등 설정가능
		
		MultipartConfigFactory factory = new MultipartConfigFactory();
		
		// 파일 업로드 입계값
		factory.setFileSizeThreshold(DataSize.ofBytes(fileSizeTreshold));
		
		// HTTP 요청당 파일 최대 크기
		factory.setMaxRequestSize(DataSize.ofBytes(maxRequestSize));
		
		// 개별 파일당 최대 크기
		factory.setMaxFileSize(DataSize.ofBytes(maxFileSize));
		
		// 임계값 초과 시 임시 저장 폴더 경로
		factory.setLocation(location);
		
		return factory.createMultipartConfig();
	}
	
	// MultipartResolver 객체를 생성하여 Bean으로 등록
	// -> Bean으로 등록하며 위에서 만든 MultipartConfigElement를 자동으로 사용
	@Bean
	public MultipartResolver multipartResolver() {
		// MultipartResolver : MultipartFile을 처리해주는 해결사
		// MultipartResolver는 클라이언트로부터 받은 multipart 요청을 처리하고
		// 이 중에서 업로드된 파일을 추출하여 MultipartFile 객체로 제공하는 역할
		StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
		
		return multipartResolver;
	}
}
