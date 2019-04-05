package com.hnjing.utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.hnjing.core.model.entity.MailHistory;
import com.hnjing.core.service.MailHistoryService;

@Component
@ConfigurationProperties(prefix = "spring.mail")
public class MailUtil {

	private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.profiles.active}")
	private String ispass;

	private String mailfrom;

	public String getMailfrom() {
		return mailfrom;
	}

	public void setMailfrom(String mailfrom) {
		this.mailfrom = mailfrom;
	}

	@Autowired
	private MailHistoryService mailHistoryService;

	/**
	 * 
	 * @Title: sendSimpleMail
	 * @Description: 发送简单邮件
	 * @throws MessagingException
	 *             void
	 * @author:
	 * @throws ExecutionException
	 */
	// @Async
	public Future<Integer> sendSimpleMail(String mailto, String title, String mailMsg) throws ExecutionException {
		MailHistory mailHistory = new MailHistory();
		mailHistory.setSendTo(mailto);
		mailHistory.setContent(mailMsg);
		mailHistory.setTitile(title);
		try {
			mailHistoryService.addMailHistory(mailHistory);
		}catch(Exception e) {
			
		}
		return sendSimpleMailNoRecord(mailto, title, mailMsg);
	}
		
	public Future<Integer> sendSimpleMailNoRecord(String mailto, String title, String mailMsg) throws ExecutionException {
		logger.info(title + "/r/n" + mailto + "/r/n" + mailMsg);
		AsyncResult<Integer> rsyncResult = null;
		if ("dev".equals(ispass)) {
			mailto = "hejinlong@hnjing.com";
		}
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			
			helper.setFrom("shangji@hnjing.com");
			mailto = mailto.trim().replaceAll("；", ";");
			mailto = mailto.replaceAll("，", ";");
			mailto = mailto.replaceAll(",", ";");
			if (mailto.contains(";")) {
				// 多接收人			
				helper.setTo(mailto.substring(0, mailto.indexOf(";"))); //主发送
				//抄送
				helper.setCc(mailto.substring(mailto.indexOf(";")+1).split(";"));
			} else {
				helper.setTo(mailto);// 单接收人
			}

			helper.setSubject(title);
			helper.setText(mailMsg, true);
			mailSender.send(mimeMessage);
			rsyncResult = new AsyncResult<>(1);
		} catch (MessagingException e) {
			e.printStackTrace();
			rsyncResult = new AsyncResult<>(0);
		}
		return rsyncResult;
	}
}
