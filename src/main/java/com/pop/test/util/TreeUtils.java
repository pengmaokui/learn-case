package com.pop.test.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Created by pengmaokui on 2018/3/21.
 */
public class TreeUtils {
	public static List<TreeObj> handlerMoreAndMore(List<TreeObj> list) {
		Map<Integer, TreeObj> map = new HashMap<>(list.size() * 2);
		List<TreeObj> result = new ArrayList<>();
		for (TreeObj treeObj : list) {
			TreeObj obj = map.get(treeObj.getId());
			if (obj == null) {
				map.put(treeObj.getId(), treeObj);
			}
		}

		for (TreeObj treeObj : list) {
			Integer parentId = treeObj.getParentId();
			TreeObj obj = map.get(treeObj.getId());
			if (parentId == null) {
				//父级
				result.add(obj);
				continue;
			}
			TreeObj parentObj = map.get(parentId);
			if (parentObj != null) {
				List<TreeObj> childrenList = parentObj.getChildrenList();
				if (childrenList == null) {
					childrenList = new ArrayList<>();
					parentObj.setChildrenList(childrenList);
				}
				childrenList.add(obj);
			}
		}

		return result;
	}


	public static void main(String[] args) {
		List<TreeObj> treeObjList = new ArrayList<>();
		treeObjList.add(new TreeObj(1, null, "功能1"));
		treeObjList.add(new TreeObj(2, 1, "功能2"));
		treeObjList.add(new TreeObj(3, 2, "功能3"));
		treeObjList.add(new TreeObj(4, 3, "功能4"));
		treeObjList.add(new TreeObj(3, 5, "功能5"));
		treeObjList.add(new TreeObj(5, 1, "功能5"));
		treeObjList = TreeUtils.handlerMoreAndMore(treeObjList);
		Gson gson = new GsonBuilder().create();
		System.out.println(gson.toJson(treeObjList));
	}

	private static class TreeObj {
		private Integer id;

		private Integer parentId;

		private String name;

		private List<TreeObj> childrenList;

		public TreeObj(Integer id, Integer parentId, String name) {
			this.id = id;
			this.parentId = parentId;
			this.name = name;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getParentId() {
			return parentId;
		}

		public void setParentId(Integer parentId) {
			this.parentId = parentId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<TreeObj> getChildrenList() {
			return childrenList;
		}

		public void setChildrenList(List<TreeObj> childrenList) {
			this.childrenList = childrenList;
		}
	}
}
