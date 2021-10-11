package com.lc.ddns;

import com.lc.ddns.service.DdnsService;
import com.lc.ddns.util.MyUtils;

/**
 * 运行时入口
 *
 * @author LC
 */
public class AppRun {
	private static DdnsService ddnsService = new DdnsService();
	private static Thread serviceThread = null;

	public static void main(String[] args) {
		while (true) {
			if (serviceThread == null || !serviceThread.isAlive()) {
				serviceThread = new Thread() {
					@Override
					public void run() {
						super.run();
						ddnsService.Start();
					}
				};
				serviceThread.start();
			}
			// 睡60秒
			MyUtils.sleep(60000);
		}
	}
}
