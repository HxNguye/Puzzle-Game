package exceptions;


//This exception is thrown when an invalid port number such as 0 is shown
/**
 * @author Antonio Rend�n
 */
@SuppressWarnings("serial")
public class InvalidPortNumberException extends Exception
{
	public InvalidPortNumberException(String msg)
	{
		super(msg);
	}

}
