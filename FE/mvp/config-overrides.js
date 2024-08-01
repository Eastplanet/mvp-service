module.exports = function override(config, env) {
    if (env === 'development') {
        config.devServer = {
            ...config.devServer,
            // 필요한 설정 추가
            disableHostCheck: true, // 호스트 검사를 비활성화합니다.
            // 다음과 같은 설정을 추가할 수 있습니다:
            // host: '0.0.0.0', // 모든 호스트에서 접근 가능
            // port: 3000, // 사용하고자 하는 포트 번호
            // allowedHosts: ['.yourdomain.com'], // 특정 도메인을 허용
        };
    }
  
    return config;
  };