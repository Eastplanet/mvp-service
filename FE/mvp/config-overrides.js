// config-overrides.js
module.exports = function override(config, env) {
    console.log('Overriding devServer configuration'); // 로그 추가
    config.devServer = {
      ...config.devServer,
      disableHostCheck: true,
      allowedHosts: 'all',
    };
    return config;
  };
  