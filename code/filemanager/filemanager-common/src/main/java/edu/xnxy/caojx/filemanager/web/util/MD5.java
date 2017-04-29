package edu.xnxy.caojx.filemanager.web.util;

//import required classes

public class MD5 {
	private static MD5 md5 = null;

	static final int S11 = 7;

	static final int S12 = 12;

	static final int S13 = 17;

	static final int S14 = 22;

	static final int S21 = 5;

	static final int S22 = 9;

	static final int S23 = 14;

	static final int S24 = 20;

	static final int S31 = 4;

	static final int S32 = 11;

	static final int S33 = 16;

	static final int S34 = 23;

	static final int S41 = 6;

	static final int S42 = 10;

	static final int S43 = 15;

	static final int S44 = 21;

	static final byte PADDING[] = { -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0 };

	private long state[];

	private long count[];

	private byte buffer[];

	public String digestHexStr;

	private byte digest[];

	public static synchronized MD5 getInstance() {
		if (md5 == null)
			md5 = new MD5();
		return md5;
	}

	public String getMD5ofStr(String s) {
		md5Init();
		md5Update(s.getBytes(), s.length());
		md5Final();
		digestHexStr = "";
		for (int i = 0; i < 16; i++) {
			digestHexStr += byteHEX(digest[i]);
		}

		return digestHexStr;
	}

	private MD5() {
		state = new long[4];
		count = new long[2];
		buffer = new byte[64];
		digest = new byte[16];
		md5Init();
	}

	private void md5Init() {
		count[0] = 0L;
		count[1] = 0L;
		state[0] = 0x67452301L;
		state[1] = 0xefcdab89L;
		state[2] = 0x98badcfeL;
		state[3] = 0x10325476L;
	}

	private long F(long l, long l1, long l2) {
		return l & l1 | ~l & l2;
	}

	private long G(long l, long l1, long l2) {
		return l & l2 | l1 & ~l2;
	}

	private long H(long l, long l1, long l2) {
		return l ^ l1 ^ l2;
	}

	private long I(long l, long l1, long l2) {
		return l1 ^ (l | ~l2);
	}

	private long FF(long l, long l1, long l2, long l3, long l4, long l5, long l6) {
		l += F(l1, l2, l3) + l4 + l6;
		l = (int) l << (int) l5 | (int) l >>> (int) (32L - l5);
		l += l1;
		return l;
	}

	private long GG(long l, long l1, long l2, long l3, long l4, long l5, long l6) {
		l += G(l1, l2, l3) + l4 + l6;
		l = (int) l << (int) l5 | (int) l >>> (int) (32L - l5);
		l += l1;
		return l;
	}

	private long HH(long l, long l1, long l2, long l3, long l4, long l5, long l6) {
		l += H(l1, l2, l3) + l4 + l6;
		l = (int) l << (int) l5 | (int) l >>> (int) (32L - l5);
		l += l1;
		return l;
	}

	private long II(long l, long l1, long l2, long l3, long l4, long l5, long l6) {
		l += I(l1, l2, l3) + l4 + l6;
		l = (int) l << (int) l5 | (int) l >>> (int) (32L - l5);
		l += l1;
		return l;
	}

	private void md5Update(byte abyte0[], int i) {
		byte abyte1[] = new byte[64];
		int k = (int) (count[0] >>> 3) & 0x3f;
		if ((count[0] += i << 3) < (long) (i << 3)) {
			count[1]++;
		}
		count[1] += i >>> 29;
		int l = 64 - k;
		int j;
		if (i >= l) {
			md5Memcpy(buffer, abyte0, k, 0, l);
			md5Transform(buffer);
			for (j = l; j + 63 < i; j += 64) {
				md5Memcpy(abyte1, abyte0, 0, j, 64);
				md5Transform(abyte1);
			}

			k = 0;
		} else {
			j = 0;
		}
		md5Memcpy(buffer, abyte0, k, j, i - j);
	}

	private void md5Final() {
		byte abyte0[] = new byte[8];
		Encode(abyte0, count, 8);
		int i = (int) (count[0] >>> 3) & 0x3f;
		int j = i >= 56 ? 120 - i : 56 - i;
		md5Update(PADDING, j);
		md5Update(abyte0, 8);
		Encode(digest, state, 16);
	}

	private void md5Memcpy(byte abyte0[], byte abyte1[], int i, int j, int k) {
		for (int l = 0; l < k; l++) {
			abyte0[i + l] = abyte1[j + l];
		}

	}

	private void md5Transform(byte abyte0[]) {
		long l = state[0];
		long l1 = state[1];
		long l2 = state[2];
		long l3 = state[3];
		long al[] = new long[16];
		Decode(al, abyte0, 64);
		l = FF(l, l1, l2, l3, al[0], 7L, 0xd76aa478L);
		l3 = FF(l3, l, l1, l2, al[1], 12L, 0xe8c7b756L);
		l2 = FF(l2, l3, l, l1, al[2], 17L, 0x242070dbL);
		l1 = FF(l1, l2, l3, l, al[3], 22L, 0xc1bdceeeL);
		l = FF(l, l1, l2, l3, al[4], 7L, 0xf57c0fafL);
		l3 = FF(l3, l, l1, l2, al[5], 12L, 0x4787c62aL);
		l2 = FF(l2, l3, l, l1, al[6], 17L, 0xa8304613L);
		l1 = FF(l1, l2, l3, l, al[7], 22L, 0xfd469501L);
		l = FF(l, l1, l2, l3, al[8], 7L, 0x698098d8L);
		l3 = FF(l3, l, l1, l2, al[9], 12L, 0x8b44f7afL);
		l2 = FF(l2, l3, l, l1, al[10], 17L, 0xffff5bb1L);
		l1 = FF(l1, l2, l3, l, al[11], 22L, 0x895cd7beL);
		l = FF(l, l1, l2, l3, al[12], 7L, 0x6b901122L);
		l3 = FF(l3, l, l1, l2, al[13], 12L, 0xfd987193L);
		l2 = FF(l2, l3, l, l1, al[14], 17L, 0xa679438eL);
		l1 = FF(l1, l2, l3, l, al[15], 22L, 0x49b40821L);
		l = GG(l, l1, l2, l3, al[1], 5L, 0xf61e2562L);
		l3 = GG(l3, l, l1, l2, al[6], 9L, 0xc040b340L);
		l2 = GG(l2, l3, l, l1, al[11], 14L, 0x265e5a51L);
		l1 = GG(l1, l2, l3, l, al[0], 20L, 0xe9b6c7aaL);
		l = GG(l, l1, l2, l3, al[5], 5L, 0xd62f105dL);
		l3 = GG(l3, l, l1, l2, al[10], 9L, 0x2441453L);
		l2 = GG(l2, l3, l, l1, al[15], 14L, 0xd8a1e681L);
		l1 = GG(l1, l2, l3, l, al[4], 20L, 0xe7d3fbc8L);
		l = GG(l, l1, l2, l3, al[9], 5L, 0x21e1cde6L);
		l3 = GG(l3, l, l1, l2, al[14], 9L, 0xc33707d6L);
		l2 = GG(l2, l3, l, l1, al[3], 14L, 0xf4d50d87L);
		l1 = GG(l1, l2, l3, l, al[8], 20L, 0x455a14edL);
		l = GG(l, l1, l2, l3, al[13], 5L, 0xa9e3e905L);
		l3 = GG(l3, l, l1, l2, al[2], 9L, 0xfcefa3f8L);
		l2 = GG(l2, l3, l, l1, al[7], 14L, 0x676f02d9L);
		l1 = GG(l1, l2, l3, l, al[12], 20L, 0x8d2a4c8aL);
		l = HH(l, l1, l2, l3, al[5], 4L, 0xfffa3942L);
		l3 = HH(l3, l, l1, l2, al[8], 11L, 0x8771f681L);
		l2 = HH(l2, l3, l, l1, al[11], 16L, 0x6d9d6122L);
		l1 = HH(l1, l2, l3, l, al[14], 23L, 0xfde5380cL);
		l = HH(l, l1, l2, l3, al[1], 4L, 0xa4beea44L);
		l3 = HH(l3, l, l1, l2, al[4], 11L, 0x4bdecfa9L);
		l2 = HH(l2, l3, l, l1, al[7], 16L, 0xf6bb4b60L);
		l1 = HH(l1, l2, l3, l, al[10], 23L, 0xbebfbc70L);
		l = HH(l, l1, l2, l3, al[13], 4L, 0x289b7ec6L);
		l3 = HH(l3, l, l1, l2, al[0], 11L, 0xeaa127faL);
		l2 = HH(l2, l3, l, l1, al[3], 16L, 0xd4ef3085L);
		l1 = HH(l1, l2, l3, l, al[6], 23L, 0x4881d05L);
		l = HH(l, l1, l2, l3, al[9], 4L, 0xd9d4d039L);
		l3 = HH(l3, l, l1, l2, al[12], 11L, 0xe6db99e5L);
		l2 = HH(l2, l3, l, l1, al[15], 16L, 0x1fa27cf8L);
		l1 = HH(l1, l2, l3, l, al[2], 23L, 0xc4ac5665L);
		l = II(l, l1, l2, l3, al[0], 6L, 0xf4292244L);
		l3 = II(l3, l, l1, l2, al[7], 10L, 0x432aff97L);
		l2 = II(l2, l3, l, l1, al[14], 15L, 0xab9423a7L);
		l1 = II(l1, l2, l3, l, al[5], 21L, 0xfc93a039L);
		l = II(l, l1, l2, l3, al[12], 6L, 0x655b59c3L);
		l3 = II(l3, l, l1, l2, al[3], 10L, 0x8f0ccc92L);
		l2 = II(l2, l3, l, l1, al[10], 15L, 0xffeff47dL);
		l1 = II(l1, l2, l3, l, al[1], 21L, 0x85845dd1L);
		l = II(l, l1, l2, l3, al[8], 6L, 0x6fa87e4fL);
		l3 = II(l3, l, l1, l2, al[15], 10L, 0xfe2ce6e0L);
		l2 = II(l2, l3, l, l1, al[6], 15L, 0xa3014314L);
		l1 = II(l1, l2, l3, l, al[13], 21L, 0x4e0811a1L);
		l = II(l, l1, l2, l3, al[4], 6L, 0xf7537e82L);
		l3 = II(l3, l, l1, l2, al[11], 10L, 0xbd3af235L);
		l2 = II(l2, l3, l, l1, al[2], 15L, 0x2ad7d2bbL);
		l1 = II(l1, l2, l3, l, al[9], 21L, 0xeb86d391L);
		state[0] += l;
		state[1] += l1;
		state[2] += l2;
		state[3] += l3;
	}

	private void Encode(byte abyte0[], long al[], int i) {
		int j = 0;
		for (int k = 0; k < i; k += 4) {
			abyte0[k] = (byte) (int) (al[j] & 255L);
			abyte0[k + 1] = (byte) (int) (al[j] >>> 8 & 255L);
			abyte0[k + 2] = (byte) (int) (al[j] >>> 16 & 255L);
			abyte0[k + 3] = (byte) (int) (al[j] >>> 24 & 255L);
			j++;
		}

	}

	private void Decode(long al[], byte abyte0[], int i) {
		int j = 0;
		for (int k = 0; k < i; k += 4) {
			al[j] = b2iu(abyte0[k]) | b2iu(abyte0[k + 1]) << 8
					| b2iu(abyte0[k + 2]) << 16 | b2iu(abyte0[k + 3]) << 24;
			j++;
		}

	}

	public static long b2iu(byte byte0) {
		return byte0 >= 0 ? byte0 : byte0 & 0xff;
	}

	public static String byteHEX(byte byte0) {
		char ac[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char ac1[] = new char[2];
		ac1[0] = ac[byte0 >>> 4 & 0xf];
		ac1[1] = ac[byte0 & 0xf];
		String s = new String(ac1);
		return s;
	}

	public static String getMD5Str(String string) {
		return getInstance().getMD5ofStr(string);
	}

	public static void main(String args[]) {
		MD5 md5 = new MD5();
		System.out.println(md5.getMD5ofStr("201314070227"));
	}
}
