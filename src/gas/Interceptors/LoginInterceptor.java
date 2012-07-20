package gas.Interceptors;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LoginInterceptor implements Interceptor
{
	private static final long serialVersionUID = 6792330842334679646L;

	@Override
	public void destroy() {}
	@Override
	public void init() {}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception
	{
		Map<String, Object> context = invocation.getInvocationContext().getSession();
		//System.out.println("Intercettore: " + context.containsKey("user") + ", " + context.containsKey("status"));
		//System.out.println(invocation.getAction().getClass().getName());
		
		if(invocation.getAction().getClass().getName().equals("gas.Controller.LoginAction")){
			//System.out.println("1");
			if(context.containsKey("user")){
				//System.out.println("2");
				return "loggato";
			} else {
				//System.out.println("3");
				return invocation.invoke();
			}
		} else {
			if(context.containsKey("user")){
				//System.out.println("4");
				return invocation.invoke();
			} else {
				//System.out.println("5");
				return "login";
			}
		}		
	}
}
