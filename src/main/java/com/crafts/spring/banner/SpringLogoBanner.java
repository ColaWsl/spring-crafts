package com.crafts.spring.banner;

public class SpringLogoBanner implements Banner{

	@Override
	public void showBanner() {
		String[] banner = {
				"  _____ _                 _        _____           _       ",
				" / ____| |               | |      / ____|         | |      ",
				"| |  __| | ___   ___   __| | ___ | |     ___   __| | ___  ",
				"| | |_ | |/ _ \\ / _ \\ / _` |/ _ \\| |    / _ \\ / _` |/ _ \\ ",
				"| |__| | | (_) | (_) | (_| |  __/| |___| (_) | (_| |  __/ ",
				" \\_____|_|\\___/ \\___/ \\__,_|\\___|\\_____/\\___/ \\__,_|\\___| "
		};

		for (String s : banner) {
			System.out.println(s);
		}
	}
}
