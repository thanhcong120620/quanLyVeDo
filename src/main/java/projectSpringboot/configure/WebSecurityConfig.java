package projectSpringboot.configure;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String ADMIN = "ADMIN";
//	private static final String USER = "USER";

	@Autowired
	private UserDetailsService userDetailsService;
	
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf()
				.disable()
				.authorizeRequests()
				.antMatchers("/passwordMail","/createUser","/passwordSupport","/updateAndSave","/cusLottery","/cusHistory/**").permitAll()
//				.antMatchers("/cusLottery*").permitAll()
				.antMatchers("/admin/**").hasRole(ADMIN)
//				.antMatchers("/user/**").hasAnyRole(ADMIN, USER)
//				.anyRequest().authenticated()
				.and()
				.oauth2Login()

			 //check login
            	.and()
            	.formLogin()
            	.loginPage("/login")
            	.usernameParameter("mailUser")
            	.passwordParameter("passwordUser")
//            	.loginProcessingUrl("/loginUser")
            	.defaultSuccessUrl("/landingPage")
            	.failureUrl("/login?error=true")

//				.loginProcessingUrl("/loginDirect")
//		    	.defaultSuccessUrl("/admin")
//		    	.failureUrl("/login.html?error=true")
//		    	.failureUrl("/403")
		   /** 
		    * loginPage() – the custom login page
		    * loginProcessingUrl() – the URL to submit the username and password to
		    * defaultSuccessUrl() – the landing page after a successful login
		    * failureUrl() – the landing page after an unsuccessful login
		    * logoutUrl() – the custom logout
		    */
          //when you logout
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login")
//            .invalidateHttpSession(true)
//            .deleteCookies("JSESSIONID")
		    .and()
//			http.csrf().disable()
			  .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
			
		// Config Remember Me.
		/*
		 * http.authorizeRequests().and() //
		 * .rememberMe().tokenRepository(this.persistentTokenRepository()) //
		 * .tokenValiditySeconds(1 * 24 * 60 * 60); // 24h
		 */	}
	/*
	 * @Bean public PersistentTokenRepository persistentTokenRepository() {
	 * JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
	 * db.setDataSource(dataSource); return db; }
	 */

	/*
	 * @Bean public PasswordEncoder getPasswordEncoder() { // return new
	 * BCryptPasswordEncoder();
	 * System.out.println(NoOpPasswordEncoder.getInstance()); return
	 * NoOpPasswordEncoder.getInstance(); }
	 */
	
//    @Bean
//    public PasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }//ma hoa password
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
}
