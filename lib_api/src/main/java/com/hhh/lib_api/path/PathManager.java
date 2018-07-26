package com.hhh.lib_api.path;

import com.hhh.lib_core.model.WeConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 将不同模块的path地址进行统一的管理
 * 对path按照需求进行相应的改变,如同意添加token等
 * Created by nova on 13/10/2017.
 */

public class PathManager {
    private static final String TAG = PathManager.class.getSimpleName();
    public static final String IMAGE_PERFIX = WeConstants.WEBSITE;
    private static PathManager mInstance;

    private List<WePath> mPaths;

    private PathManager() {
        mPaths = new ArrayList<>();
        mPaths.addAll(SamplePath.INDEX_PATHS);
    }

    public static PathManager getInstance() {
        if (mInstance == null) {
            mInstance = new PathManager();
        }

        return mInstance;
    }

    public boolean isNeedToken(String path) {
        for (WePath p :
            mPaths) {
            if (p.path.equals(path)) return p.auth;

            if (p.path.contains("%")) {
              // 注意了 需要额外解析了
              String[] urlArray = path.split("/");
              String[] defineArray = p.path.split("/");

              if (defineArray.length != urlArray.length) continue;

              boolean absolutelySame = true;
              // 逐一检查
              for (int i=0, length = defineArray.length; i<length; ++i) {
                if (!defineArray[i].contains("%")) {
                  absolutelySame = defineArray[i].equals(urlArray[i]);

                  if (!absolutelySame) break;
                }
              }

              if (absolutelySame) return p.auth;
            }
        }
        return true;
    }

}
