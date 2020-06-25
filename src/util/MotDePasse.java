
package util;



public class MotDePasse {
    public static String generate(int securityLevel)
{
	    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // Tu supprimes les lettres dont tu ne veux pas
	    String pass = "";
	    for(int x=0;x<securityLevel;x++)
	    {
	       int i = (int)Math.floor(Math.random() * 62); // Si tu supprimes des lettres tu diminues ce nb
	       pass += chars.charAt(i);
	    }
	    System.out.println(pass);
	    return pass;
}
	public static String base(String list,int securityLevel)
	{    String pass = ""+list+"_";

		int length=list.length();
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // Tu supprimes les lettres dont tu ne veux pas
		if(securityLevel>length){
			for(int x=0;x<securityLevel-list.length();x++)
			{
				int i = (int)Math.floor(Math.random() * 62); // Si tu supprimes des lettres tu diminues ce nb
				pass += chars.charAt(i);
			}
		}
		if(securityLevel<=length){
				int i = (int)Math.floor(Math.random() * 62); // Si tu supprimes des lettres tu diminues ce nb
				pass += chars.charAt(i);
		}


		System.out.println(pass);
		return pass;
	}

    
}
