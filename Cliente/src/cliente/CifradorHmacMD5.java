package cliente;

import java.security.MessageDigest;

public class CifradorHmacMD5 {

	private byte[] getKeyedDigest(byte[] buffer) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(buffer);
			return md5.digest();
		} catch (Exception e) {
			return null;
		}
	}

	public byte[] calcular(String dato) {
		try {
			byte[] text = dato.getBytes();
			String s1 = new String(text);
			System.out.println("Dato original: " + s1);
			byte [] digest = getKeyedDigest(text);
			String s2 = new String(digest);
			System.out.println("Digest: "+ s2);
			return digest;
		}
		catch (Exception e) {
			System.out.println("Excepción: " + e.getMessage());
			return null;
		}
	}
	
	public boolean sonIguales(String msj, byte[] digest) {
		boolean son= true;
		byte[] mensaje = calcular(msj);
		if (digest.length == mensaje.length){
			for (int i=0; i<mensaje.length && son;i++){
				if (digest[i]!=mensaje[i]) {
					son = false;
				}
			}
		}
		else {
			son = false;
		}
		
		return son;
	}

}
