package com.pop.test.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

/**
 * Created by pengmaokui on 2018/6/28.
 */
public class Finder {

	private String fileParentPath;

	/**
	 * 文件名称
	 */
	private String filePath;

	/**
	 * 取出前多少位频次高的单词
	 */
	private int topWord;

	/**
	 * 取出前多少位频次高的字母
	 */
	private int topAlphabet;

	/**
	 * 文件的最大大小 单位是b
	 * 即21MB
	 */
	private static final long FILE_MAX_SIZ = 21 << 10 << 10;

	/**
	 * 存储切割之后子文件路径
	 */
	private Set<String> subFilePath = new HashSet<>();

	/**
	 * 文件长度
	 */
	private int lineNum = 0;

	/**
	 * 存储字母出现频率
	 */
	private Map<String, Long> alphabetMap = new HashMap<>();

	Finder (String filePath, int topWord, int topAlphabet) {
		this.filePath = filePath;
		this.fileParentPath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
		this.topWord = topWord;
		this.topAlphabet = topAlphabet;
	}

	public void find() {
		long startTime = System.currentTimeMillis();
		File orgFile = new File(this.filePath);
		//切割文件
		cutFile(orgFile, true);
		for (;;) {
			boolean breakFlag = true;
			for (String filePath : subFilePath) {
				File subFile = new File(filePath);
				long fileSize = getFileSiz(subFile);
				//文件过大 重新切割
				if (fileSize > FILE_MAX_SIZ) {
					breakFlag = false;
					cutFile(subFile, false);
				}
			}
			if (breakFlag) {
				break;
			}
		}
		//切割文件完成，开始统计
		HashMap<String, Long> allCountWordsMap = new HashMap<>();
		for (String filePath : subFilePath) {
			Map<String, Long> map = countWord(filePath);
			if (map != null) {
				allCountWordsMap.putAll(map);
			}
		}
		//获取出现频次最高的单词
		Map<String, Long> countWordsMap = orderHashMap(allCountWordsMap, topWord);

		//获取出现频次最高的字母
		Map<String, Long> countAlphabetMap = orderHashMap(alphabetMap, topAlphabet);

		for (String key : countWordsMap.keySet()) {
			System.out.println("单词：" + key + ",出现次数：" + countWordsMap.get(key));
		}

		for (String key : countAlphabetMap.keySet()) {
			System.out.println("字母：" + key + ",出现次数：" + countAlphabetMap.get(key));
		}
		System.out.println("文件行数：" + lineNum);

		System.out.println("耗时：" + (System.currentTimeMillis() - startTime) + "ms");
	}

	private void cutFile(File file, boolean orgFlag) {
		String fileNamePre = UUID.randomUUID().toString();

		long fileSize = getFileSiz(file);
		int count = (int) Math.ceil(fileSize * 1.0 / FILE_MAX_SIZ);
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(new FileInputStream(file));
			br = new BufferedReader(isr);

			while(br.read() != -1) // read()=-1代表数据读取完毕
			{
				String s = br.readLine();
				String[] words = s.split(" ");
				for (String word : words) {
					if (StringUtils.isBlank(word)) {
						//空字符不校验
						continue;
					}
					//解析字母出现次数
					//解析源文件才执行
					if (orgFlag) {
						countAlphabet(word);
					}
					int fileNameNum = hash(word) % count;
					writeWord(word, fileNamePre, fileNameNum + "");
				}
				if (orgFlag) {
					lineNum++; //行数加1
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取文件的大小
	 */
	private long getFileSiz(File file) {
		if(file.exists() && file.isFile()){
			return file.length();
		}
		throw new RuntimeException("文件不存在");
	}

	private void countAlphabet(String word) {
		for(int i = 0; i < word.length(); i++) {
			String s = word.charAt(i) + "";
			if (alphabetMap.containsKey(s)) {
				alphabetMap.put(s, alphabetMap.get(s) + 1L);
			} else {
				alphabetMap.put(s, 1L);
			}
		}
	}

	private void writeWord(String word, String fileNamePre, String fileNameNum) {
		String filePath = this.fileParentPath + fileNamePre + fileNameNum;
		subFilePath.add(filePath);

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true)));
			out.write(word + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(out != null){
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Map<String, Long> countWord(String filePath) {
		File file = new File(filePath);
		InputStreamReader isr = null;
		BufferedReader br = null;
		Map<String, Long> map = new HashMap<>();
		try {
			isr = new InputStreamReader(new FileInputStream(file));
			br = new BufferedReader(isr);

			while(br.read() != -1) // read()=-1代表数据读取完毕
			{
				String s = br.readLine();
				//只有一个单词
				if (map.containsKey(s)) {
					map.put(s, map.get(s) + 1L);
				} else {
					map.put(s, 1L);
				}
			}
			Map<String, Long> orderHashMap =  orderHashMap(map, topWord);
			map = null; //help gc;
			return orderHashMap;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private Map<String, Long> orderHashMap(Map<String, Long> map, int top) {
		List<Map.Entry<String, Long>> list = new ArrayList<>();
		list.addAll(map.entrySet());
		ValueComparator vc = new ValueComparator();
		Collections.sort(list , vc);
		Map<String, Long> result = new HashMap<>();
		int i = 0;
		for(Iterator<Map.Entry<String, Long>> it = list.iterator(); it.hasNext() && i < top; i++)
		{
			Map.Entry<String, Long> item =  it.next();
			result.put(item.getKey(), item.getValue());
		}

		return result;
	}

	private int hash(String key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

	private static class ValueComparator implements Comparator<Map.Entry<String, Long>>
	{
		public int compare(Map.Entry<String, Long> m, Map.Entry<String, Long> n)
		{
			return (int) (n.getValue() - m.getValue());
		}
	}

	public static void main(String[] args) {
		String filePath = "文件路劲";
		Finder finder = new Finder(filePath, 100, 10);
		finder.find();
	}

}
