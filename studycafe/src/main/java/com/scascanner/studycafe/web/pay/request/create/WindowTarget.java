package com.scascanner.studycafe.web.pay.request.create;

/**
 * 브라우저에서 결제창이 열리는 프레임을 지정합니다. self, iframe 중 하나입니다.
 * 값을 넣지 않으면 iframe에서 결제창이 열립니다. 현재창에서 결제창으로 이동시키는 방식을 사용하려면 값을 self로 지정하세요.
 * * 모바일 웹에서는 windowTarget 값과 상관없이 항상 현재창에서 결제창으로 이동합니다.
 */
public enum WindowTarget {
    self, iframe
}
