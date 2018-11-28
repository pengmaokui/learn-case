package com.pop.test.jdk.nio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by pengmaokui on 2017/8/31.
 */
public class FileTest {

	private static final String COMMA = ",";

	private Gson gson = new GsonBuilder().create();

	@Test
	public void skipLineNum() throws IOException {
		String inputFilePath = "/Users/pengmaokui/Downloads/TRADE_2017-06-01.csv";
		long lineNum = 566;
		long batchNum = 10;


		// skip the header of the csv
		for (int i = 0; i < 566/10; i++) {
			int finalI = i;
			((Runnable) () -> {
				try {
					File inputF = new File(inputFilePath);
					InputStream inputFS = null;
					inputFS = new FileInputStream(inputF);
					BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

					long skip = finalI * batchNum;
					List<String[]> inputList = br.lines().skip(skip).limit(10).map(line -> {
						String[] result = line.split(COMMA);
						return result;
					}).collect(Collectors.toList());
					System.out.println(gson.toJson(inputList));
					br.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}).run();
		}

	}
}
