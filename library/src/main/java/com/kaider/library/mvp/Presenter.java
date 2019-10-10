package com.kaider.library.mvp;

import android.content.Context;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;

/**
 * @Author： KaiDer
 * @Date： 2019/7/6 20:51
 * @Description: MvpPresenter层
 * @Email： 2024468244@qq.com
 */
public class Presenter implements Contract.IPresenter<Contract.IView, Object> {
    private static final String TAG = "Presenter";
    private Model model;
    private Contract.IView iView;
    private SoftReference<Contract.IView> iViewSoftReference;
    private SoftReference<Model> modelSoftReference;

    /**
     * 绑定方法
     *
     * @param iView
     */
    @Override
    public void attach(Contract.IView iView) {
        this.iView = iView;
        model = new Model();
        //开启软引用
        iViewSoftReference = new SoftReference<>(iView);
        modelSoftReference = new SoftReference<>(model);

    }

    @Override
    public void detach() {
        //清理内存
        iViewSoftReference.clear();
        modelSoftReference.clear();
    }

    @Override
    public void mutualByGet(String url, Object bean) {
        model.getRequestByGet(url, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                //判空处理
                if (bean == null) {
                    return;
                }
                //返回请求结果
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                //返回失败结果
                iView.onFail(throwable);
            }
        });
    }

    @Override
    public void mutualByGetParam(String url, Map<String, String> queryMap, Object bean) {
        model.getRequestByGetParam(url, queryMap, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onFail(throwable);
            }
        });
    }

    @Override
    public void mutualByGetHeader(Context context, String url, Object bean) {
        model.getRequestByGetHeader(context, url, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onSuccess(throwable);
            }
        });
    }

    @Override
    public void mutualByGetParamAndHeader(Context context, String url, Map<String, String> queryMap, Object bean) {
        model.getRequestByGetParamAndHeader(context, url, queryMap, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onFail(throwable);
            }
        });
    }

    @Override
    public void mutualByPostParam(String url, Map<String, String> queryMap, Object bean) {
        model.getRequestByPostParam(url, queryMap, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onFail(throwable);
            }
        });
    }

    @Override
    public void mutualByPostHeader(Context context, String url, Object bean) {
        model.getRequestByPostHeader(context, url, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onFail(throwable);
            }
        });
    }

    @Override
    public void mutualByPostParamAndHeader(Context context, String url, Map<String, String> queryMap, Object bean) {
        model.getRequestByPostParamAndHeader(context, url, queryMap, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onFail(throwable);
            }
        });
    }

    @Override
    public void mutualByPostFieldAndHeader(Context context, String url, Map<String, String> fieldMap, Object bean) {
        model.getRequestByPostFieldAndHeader(context, url, fieldMap, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onFail(throwable);
            }
        });
    }

    @Override
    public void mutualByPostFileAndHeader(Context context, String url, File file, Object bean) {
        model.getRequestByPostFileAndHeader(context, url, file, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onFail(throwable);
            }
        });
    }

    @Override
    public void mutualByPutParamAndHeader(Context context, String url, Map<String, String> queryMap, Object bean) {
        model.getRequestByPutParamAndHeader(context, url, queryMap, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onFail(throwable);
            }
        });
    }

    @Override
    public void mutualByPostBodyAndHeader(Context context, String url, String json, Object bean) {
        model.getRequestByPostBodyAndHeader(context, url, json, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onFail(throwable);
            }
        });
    }

    @Override
    public void mutualByPostFileAndParamAndHeader(Context context, String url, List<File> files, Map<String, String> paramMap, Object bean) {
        model.getRequestByPostFilesAndParamAndHeader(context, url, files, paramMap, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onFail(throwable);
            }
        });
    }

    @Override
    public void mutualByDeleteParamAndHeader(Context context, String url, Map<String, String> paramMap, Object bean) {
        model.getRequestByDeleteParamAndHeader(context, url, paramMap, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onFail(throwable);
            }
        });
    }

    @Override
    public void mutualByPostFileParamAndHeader(Context context, String url, File file, Map<String, String> paramMap, Object bean) {
        model.getRequestByPostFileParamAndHeader(context, url, file, paramMap, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onFail(throwable);
            }
        });
    }

    @Override
    public void mutualByPost(String url, Object bean) {
        model.getRequestByPost(url, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onFail(throwable);
            }
        });
    }

    @Override
    public void mutualByPostParamAndField(String url, Map<String, String> paramMap, Map<String, String> fieldMap, Object bean) {
        model.getRequestByPostParamAndField(url, paramMap, fieldMap, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onFail(throwable);
            }
        });
    }

    @Override
    public void mutualByPutFieldAndHeader(Context context, String url, Map<String, String> fieldMap, Object bean) {
        model.getRequestByPutFieldAndHeader(context, url, fieldMap, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onFail(throwable);
            }
        });
    }

    @Override
    public void mutualByDelete(String url, Object bean) {
        model.getRequestByDelete(url, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onFail(throwable);
            }
        });
    }

    @Override
    public void mutualByDeleteParam(String url, Map<String, String> paramMap, Object bean) {
        model.getRequestByDeleteParam(url, paramMap, bean, new Contract.IModel.CallRequestData() {
            @Override
            public void onRequestSuccess(Object bean) {
                if (bean == null) {
                    return;
                }
                iView.onSuccess(bean);
            }

            @Override
            public void onRequestFail(Throwable throwable) {
                iView.onFail(throwable);
            }
        });
    }


}
