// config-overrides.js
module.exports = function override(config, env) {
    if (env === 'development') {
      console.log('Overriding devServer configuration'); // 로그 추가
      config.devServer = {
        ...config.devServer,
        disableHostCheck: true, // 호스트 검사를 비활성화
      };
    }
    return config;
  };
  